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
package io.github.prpc.discover;

import io.github.prpc.URL;
import io.github.prpc.utils.IOUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.SneakyThrows;

/**
 * 基于文件的服务注册与发现<br> 主要是实现功能 不考虑性能
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class LocalFileServiceRegisterDiscover implements IServiceRegisterDiscover {

    //用户目录 eg: /Users/pleuvoir
    private static final String USER_HOME = System.getProperty("user.home");
    //行分隔符
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");


    private Set<StringBuffer> buffer = new CopyOnWriteArraySet<>();
    private static Map<String, URL> cache = new ConcurrentHashMap<>();


    @Override
    public void register(String serviceName, URL url) {
        String root = USER_HOME + "/prpc";
        //如无则创建目录
        if (!IOUtils.mkdirs(root)) {
            return;
        }

        final String host = url.getHost();
        final Integer port = url.getPort();

        final StringBuffer lineBuffer = new StringBuffer().append(serviceName).append("=").append(host).append(":").append(port)
                .append(LINE_SEPARATOR);

        this.buffer.add(lineBuffer);

        //写入文件
        String filePath = root + "/discover.conf";

        //追加写入，不考虑重复行的问题
        IOUtils.writeToFile(filePath, lineBuffer, true);

    }


    @Override
    public void unregister(String serviceName) {
        cache.remove(serviceName);

        flushCache();
    }

    @Override
    public URL getURL(String serviceName) {
        final URL url = cache.get(serviceName);
        if (url == null) {
            load();
        }
        return cache.get(serviceName);
    }

    //读取内存中的信息全量写入文件
    private void flushCache() {
        final StringBuffer buffer = new StringBuffer();
        cache.forEach((serviceName, URL) -> {
            buffer.append(serviceName).append("=").append(URL.getHost()).append(":").append(URL.getPort())
                    .append(LINE_SEPARATOR);
        });
        String root = USER_HOME + "/prpc";
        String filePath = root + "/discover.conf";
        IOUtils.writeToFile(filePath, buffer, false);
    }

    @SneakyThrows
    private static void load() {
        String root = USER_HOME + "/prpc";
        String filePath = root + "/discover.conf";
        final List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            final String[] split = line.split("=");
            String serviceName = split[0];
            String[] serviceInfo = split[1].split(":");
            final URL url = new URL(serviceInfo[0], Integer.valueOf(serviceInfo[1]));
            cache.put(serviceName, url);
        }
    }

    public void clear() {
        cache.clear();
        flushCache();
    }

    private static volatile LocalFileServiceRegisterDiscover discover;
    private static final Object MUTEX = new Object();

    public static LocalFileServiceRegisterDiscover getInstance() {
        if (discover == null) {
            synchronized (MUTEX) {
                if (discover == null) {
                    discover = new LocalFileServiceRegisterDiscover();
                }
            }
        }
        return discover;
    }

    private LocalFileServiceRegisterDiscover() {
    }
}
