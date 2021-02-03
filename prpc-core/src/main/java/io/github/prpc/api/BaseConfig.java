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

import io.github.prpc.discover.IServiceRegisterDiscover;
import io.github.prpc.discover.LocalFileServiceRegisterDiscover;

/**
 * 基础配置<br> 服务端和消费端都会使用的一些参数
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class BaseConfig {

    {
        //TODO 临时使用
        discover = LocalFileServiceRegisterDiscover.getInstance();
    }


    private Class<?> serviceClass;

    private IServiceRegisterDiscover discover;

    public String getServiceName() {
        return serviceClass.getName();
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public IServiceRegisterDiscover getDiscover() {
        return discover;
    }

    public void setDiscover(IServiceRegisterDiscover discover) {
        this.discover = discover;
    }
}
