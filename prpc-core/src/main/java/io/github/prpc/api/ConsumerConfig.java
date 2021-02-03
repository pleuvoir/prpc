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
import io.github.prpc.discover.IServiceRegisterDiscover;

/**
 * 消费者配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConsumerConfig extends BaseConfig {

    /**
     * 从注册中心获取服务URL
     */
    public URL getServiceURL() {
        final IServiceRegisterDiscover discover = super.getDiscover();
        return discover.getURL(getServiceName());
    }
}
