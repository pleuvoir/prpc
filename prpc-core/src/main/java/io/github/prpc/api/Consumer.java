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

import io.github.prpc.invoker.ConsumerInvoker;
import io.github.prpc.proxy.ConsumerProxyAdvisor;
import io.github.prpc.proxy.JdkProxyFactory;

/**
 * 服务消费者
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Consumer extends ConsumerConfig {


    private Object proxy;

    /**
     * 动态代理从注册中心引用一个消费者
     */
    @SuppressWarnings("unchecked")
    public <T> T ref() {
        if (proxy != null) {
            return (T) proxy;
        }
        final Class<?> serviceClass = getServiceClass();
        if (serviceClass == null) {
            throw new IllegalArgumentException("ref must set serviceInterfaceClass.");
        }

        proxy = JdkProxyFactory.newProxy(serviceClass, new ConsumerProxyAdvisor(new ConsumerInvoker(this)));
        return (T) proxy;
    }

}
