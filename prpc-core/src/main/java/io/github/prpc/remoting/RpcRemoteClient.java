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

import com.alipay.remoting.rpc.RpcClient;
import io.github.prpc.RpcContext;
import io.github.prpc.RpcRequest;
import io.github.prpc.RpcResponse;
import io.github.prpc.exception.RemotingException;

/**
 * 远程请求客户端
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RpcRemoteClient {


    private static final RpcClient CLIENT = new RpcClient();

    static {
        CLIENT.init();
    }

    public static RpcRemoteClient create() {
        return new RpcRemoteClient();
    }


    public RpcResponse invokeSync(RpcContext context) throws RemotingException {
        try {
            final RpcRequest request = context.getRpcRequest();
            Object result = CLIENT.invokeSync(request.toAddress(), request, 3000);
            final RpcResponse response = new RpcResponse();
            response.setSuccess(true);
            response.setData(result);
            return response;
        } catch (Throwable e) {
            throw new RemotingException(e);
        }
    }
}
