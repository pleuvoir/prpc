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

import io.github.prpc.api.Consumer;
import io.github.prpc.test.userinterface.IEcho;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
public class ConsumerTest {

    @Test
    public void test() throws InterruptedException {

        Consumer consumer = new Consumer();
        consumer.setServiceClass(IEcho.class);

        final IEcho ref = consumer.ref();

        log.info("获取代理对象：" + ref);

        for (int i = 0; i < 100; i++) {
            final String result = ref.echo("1111");
            System.out.println(result);
            TimeUnit.SECONDS.sleep(5);
        }

    }
}
