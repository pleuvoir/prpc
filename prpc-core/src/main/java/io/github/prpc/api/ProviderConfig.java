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
import io.github.prpc.utils.ReflectUtil;

/**
 * 服务提供者配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ProviderConfig extends BaseConfig {

    //真实实现类
    private Object ref;

    //服务地址
    private URL url;

    public void setRef(Class<?> ref) {
        this.ref = ReflectUtil.newInstance(ref);
    }

    public void register(String serviceName, URL url) {
        super.getDiscover().register(serviceName, url);
    }

    public void unregister(String serviceName) {
        super.getDiscover().unregister(serviceName);
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Object getRef() {
        return ref;
    }

}
