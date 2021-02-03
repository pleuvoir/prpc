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

import com.alipay.remoting.rpc.RpcServer;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;

/**
 * 服务端暴露端口
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class RpcRemoteServer {

    private AtomicBoolean started = new AtomicBoolean(false);

    @Getter
    private RpcServer rpcServer;

    public static RpcRemoteServer create(int port) {
        return new RpcRemoteServer("127.0.0.1", port, false);
    }

    private RpcRemoteServer(String ip, int port, boolean manageConnection) {
        rpcServer = new RpcServer(ip, port, manageConnection);
        rpcServer.registerUserProcessor(RpcAcceptProcessor.INSTANCE);
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            rpcServer.start();
        }
    }

    public void stop() {
        if (started.compareAndSet(true, false)) {
            rpcServer.stop();
        }
    }
}
