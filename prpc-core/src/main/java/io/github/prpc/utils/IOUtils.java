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
package io.github.prpc.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * IO处理工作
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class IOUtils {

    public static boolean mkdirs(String dirPath) {
        final File dir = new File(dirPath);
        if (!dir.exists() && !dir.isDirectory()) {
            return dir.mkdirs();
        }
        return true;
    }

    public static boolean createNewFile(String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
                return false;
            }
        }
        return true;
    }


    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 写文件
     *
     * @param filePath 文件全路径
     * @param buffer   缓冲
     * @return 字节大小
     */
    public static int writeToFile(String filePath, StringBuffer buffer, boolean append) {
        final boolean b = IOUtils.createNewFile(filePath);
        if (!b) {
            return 0;
        }

        int size = 0;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, append);
            final byte[] bytes = buffer.toString().getBytes(Charset.defaultCharset());
            fos.write(bytes);
            fos.flush();
            size += bytes.length;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            IOUtils.close(fos);
        }
        return size;
    }

    /**
     * 写文件
     *
     * @param filePath 文件全路径
     * @param buffers  缓冲
     * @return 字节大小
     */
    public static int writeToFile(String filePath, Set<StringBuffer> buffers, boolean append) {
        final boolean b = IOUtils.createNewFile(filePath);
        if (!b) {
            return 0;
        }

        int size = 0;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, append);
            for (final StringBuffer buffer : buffers) {
                final byte[] bytes = buffer.toString().getBytes(Charset.defaultCharset());
                fos.write(bytes);
                fos.flush();
                size += bytes.length;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            IOUtils.close(fos);
            buffers.clear();
        }
        return size;
    }
}