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
package io.github.prpc.test.discover;

import io.github.prpc.URL;
import io.github.prpc.discover.LocalFileServiceRegisterDiscover;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class IServiceRegisterDiscoverTest {

    @Test
    public void testLocalFile() throws InterruptedException {
        final LocalFileServiceRegisterDiscover discover = LocalFileServiceRegisterDiscover.getInstance();

        discover.register("echo", new URL("127.0.0.1", 8081));
        discover.register("echo2", new URL("127.0.0.1", 8081));
        discover.register("echo3", new URL("127.0.0.1", 8081));

        final URL discoverURL = discover.getURL("echo");
        System.out.println(discoverURL);

        discover.unregister("echo");

        final URL discoverURLAfterDelete = discover.getURL("echo");
        System.out.println(discoverURLAfterDelete);

        TimeUnit.SECONDS.sleep(5);

        discover.clear();
    }
}
