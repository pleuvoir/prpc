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

import io.github.prpc.RpcContext;
import io.github.prpc.RpcResponse;
import io.github.prpc.exception.RemotingException;

/**
 * 短连接请求器
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class OnceRpcConnector implements IRpcConnector {

    public static final OnceRpcConnector INSTANCE = new OnceRpcConnector();

    @Override
    public RpcResponse invokeSync(RpcContext context) throws RemotingException {
        final RpcRemoteClient remoteClient = RpcRemoteClient.create();
        return remoteClient.invokeSync(context);
    }

    private OnceRpcConnector() {
    }

}
