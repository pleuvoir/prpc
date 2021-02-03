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
package io.github.prpc.remoting;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AbstractUserProcessor;
import io.github.prpc.RpcRequest;
import io.github.prpc.api.Provider;
import io.github.prpc.discover.ProviderManager;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理接收到的远程请求
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class RpcAcceptProcessor extends AbstractUserProcessor<RpcRequest> {


    public static final RpcAcceptProcessor INSTANCE = new RpcAcceptProcessor();

    @Override
    public void handleRequest(BizContext bizContext, AsyncContext asyncContext, RpcRequest rpcRequest) {

    }

    @Override
    public Object handleRequest(BizContext bizContext, RpcRequest rpcRequest) throws Exception {

        log.info("接收到客户端请求，rpcRequest={}", rpcRequest);
        final String serviceName = rpcRequest.getServiceName();

        final Provider provider = ProviderManager.getProvider(serviceName);

        final Object[] args = rpcRequest.getArgs();
        final String methodName = rpcRequest.getMethodName();
        final Class<?>[] paramTypes = rpcRequest.getParamTypes();

        //调用代理对象的方法
        final Object refProxy = provider.getRefProxy();
        final Class<?> serviceClass = provider.getServiceClass();

        final Method method = serviceClass.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);

        return method.invoke(refProxy, args);
    }

    //用户请求的类名
    // 使用String类型来避免类加载器问题
    @Override
    public String interest() {
        return RpcRequest.class.getName();
    }


    private RpcAcceptProcessor() {
    }
}
