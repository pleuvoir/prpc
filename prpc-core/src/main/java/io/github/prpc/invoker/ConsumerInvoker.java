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
import io.github.prpc.RpcResponse;
import io.github.prpc.api.Consumer;
import io.github.prpc.exception.RemotingException;
import io.github.prpc.remoting.IRpcConnector;
import io.github.prpc.remoting.OnceRpcConnector;
import lombok.Getter;

/**
 * 发起远程调用
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerInvoker implements IRpcInvoker {


    @Getter
    private Consumer consumer;

    public ConsumerInvoker(Consumer consumer) {
        this.consumer = consumer;
    }

    private final NoopInvokerListener invokerListener = new NoopInvokerListener();
    private final IRpcConnector rpcConnector = OnceRpcConnector.INSTANCE;

    @Override
    public RpcResponse invoke(RpcContext context) {

        invokerListener.preHandlerRequest(context);

        try {
            RpcResponse response = rpcConnector.invokeSync(context);
            invokerListener.postHandlerResponse(response);
            return response;
        } catch (RemotingException e) {
            invokerListener.throwable(e);
            return buildFailResponse(e);
        }
    }

    private RpcResponse buildFailResponse(Throwable throwable) {
        RpcResponse response = new RpcResponse();
        response.setSuccess(false);
        response.setThrowable(throwable);
        return response;
    }
}
