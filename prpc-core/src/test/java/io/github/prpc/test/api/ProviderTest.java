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
package io.github.prpc.test.api;

import io.github.prpc.URL;
import io.github.prpc.api.Provider;
import io.github.prpc.test.userinterface.EchoImpl;
import io.github.prpc.test.userinterface.IEcho;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ProviderTest {

    @Test
    public void testExport() throws InterruptedException {

        final Provider provider = new Provider();
        provider.setServiceClass(IEcho.class);
        provider.setRef(EchoImpl.class);
        provider.setUrl(new URL("127.0.0.1", 4399));

        provider.export();

        Thread.currentThread().join();
    }


}
