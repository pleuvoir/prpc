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
package io.github.prpc.invoker;

import io.github.prpc.RpcContext;
import io.github.prpc.RpcRequest;
import io.github.prpc.RpcResponse;
import io.github.prpc.api.Provider;
import java.lang.reflect.Method;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * 反射调用真正的实现类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ProviderInvoker implements IRpcInvoker {

    @Getter
    private Provider provider;

    public ProviderInvoker(Provider provider) {
        this.provider = provider;
    }

    @SneakyThrows
    @Override
    public RpcResponse invoke(RpcContext context) {

        final Provider provider = this.provider;

        final RpcRequest rpcRequest = context.getRpcRequest();

        final String methodName = rpcRequest.getMethodName();
        final Class<?>[] paramTypes = rpcRequest.getParamTypes();
        final Object[] args = rpcRequest.getArgs();
        final Object ref = provider.getRef();
        final Class<?> serviceClass = provider.getServiceClass();

        final Method method = serviceClass.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);

        final Object result = method.invoke(ref, args);

        final RpcResponse response = new RpcResponse();
        response.setData(result);

        return response;
    }
}
