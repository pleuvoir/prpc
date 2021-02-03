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
package io.github.prpc.api;

import io.github.prpc.URL;
import io.github.prpc.discover.ProviderManager;
import io.github.prpc.invoker.ProviderInvoker;
import io.github.prpc.proxy.JdkProxyFactory;
import io.github.prpc.proxy.ProviderProxyAdvisor;
import io.github.prpc.remoting.RpcRemoteServer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务提供者
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class Provider extends ProviderConfig {


    //实现类代理对象
    @Getter
    private Object refProxy;

    /**
     * 发布到注册中心，同时启动服务
     */
    public void export() {
        final Object ref = getRef();
        if (ref == null) {
            throw new IllegalArgumentException("export must set ref.");
        }

        final Class<?> serviceClass = getServiceClass();
        if (serviceClass == null) {
            throw new IllegalArgumentException("export must set serviceInterfaceClass.");
        }

        final URL url = getUrl();
        if (url == null) {
            throw new IllegalArgumentException("export must set url.");
        }

        //生成代理
        this.refProxy = JdkProxyFactory.newProxy(getServiceClass(), new ProviderProxyAdvisor(new ProviderInvoker(this)));
        final String serviceName = getServiceName();
        //发布到注册中心
        register(serviceName, url);
        //本地缓存
        ProviderManager.addProvider(serviceName, this);

        //启动服务
        RpcRemoteServer.create(4399).start();

        log.info("Provider server started port@{}", 4399);
    }

}
