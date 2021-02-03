/*
 * Copyright © 2021 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.prpc.proxy;

import io.github.prpc.RpcContext;
import io.github.prpc.RpcRequest;
import io.github.prpc.RpcResponse;
import io.github.prpc.api.Consumer;
import io.github.prpc.exception.PRpcRuntimeException;
import io.github.prpc.exception.ResultCodeEnum;
import io.github.prpc.invoker.ConsumerInvoker;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 完成服务节点寻找，传递给 Invoker 进行网络层调用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerProxyAdvisor implements InvocationHandler {

    private ConsumerInvoker invoker;

    public ConsumerProxyAdvisor(ConsumerInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ConsumerInvoker invoker = this.invoker;

        final Consumer consumer = invoker.getConsumer();

        // 拦截定义在 Object 类中的方法（未被子类重写），比如 toString
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }

        //构建请求上下文
        final RpcContext rpcContext = this.buildRpcContext(method, args, consumer);

        final RpcRequest rpcRequest = rpcContext.getRpcRequest();
        //获取服务节点失败
        if (rpcRequest.getUrl() == null) {
            throw new PRpcRuntimeException(ResultCodeEnum.NO_AVAILAbLE_NODE, buildFailMessage(rpcRequest));
        }

        //使用Invoker完成真正的网络调用
        final RpcResponse rpcResponse = invoker.invoke(rpcContext);
        if (rpcResponse.isSuccess()) {
            return rpcResponse.getData();
        }

        throw new PRpcRuntimeException(ResultCodeEnum.REMOTING_INVOKE_EXCEPTION, buildFailMessage(rpcRequest));
    }


    private RpcContext buildRpcContext(Method method, Object[] args, Consumer consumer) {
        final RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setUrl(consumer.getServiceURL());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArgs(args);
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setServiceName(consumer.getServiceName());

        final RpcContext rpcContext = new RpcContext();
        rpcContext.setRpcRequest(rpcRequest);
        return rpcContext;
    }

    private String buildFailMessage(RpcRequest rpcRequest) {
        return String.format("[serviceName:%s, methodName:%s]", rpcRequest.getServiceName(), rpcRequest.getMethodName());
    }
}
