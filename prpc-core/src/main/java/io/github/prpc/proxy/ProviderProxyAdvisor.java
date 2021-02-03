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
import io.github.prpc.api.Provider;
import io.github.prpc.invoker.ProviderInvoker;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理层主要加入拦截器链，这一层会被RpcAcceptor调用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ProviderProxyAdvisor implements InvocationHandler {

    private ProviderInvoker invoker;

    public ProviderProxyAdvisor(ProviderInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 拦截定义在 Object 类中的方法（未被子类重写），比如 toString
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }

        //构建请求
        final RpcContext rpcContext = this.buildRpcContext(method, args);

        final RpcResponse rpcResponse = this.invoker.invoke(rpcContext);

        return rpcResponse.getData();
    }


    private RpcContext buildRpcContext(Method method, Object[] args) {
        final Provider provider = this.invoker.getProvider();
        final RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setUrl(provider.getUrl());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArgs(args);
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setServiceName(provider.getServiceName());

        final RpcContext rpcContext = new RpcContext();
        rpcContext.setRpcRequest(rpcRequest);
        return rpcContext;
    }
}
