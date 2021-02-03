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

import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SuppressWarnings("all")
public class ReflectUtil {


    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        throw new IllegalStateException("reflect invoke error");
    }

    /**
     * 方法调用  适合无参函数调用
     *
     * @param className  类名：精确到包名
     * @param methodName 方法名
     */
    public static <T> T invoke(String className, String methodName) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName);
            return (T) method.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        throw new IllegalStateException("reflect invoke error");
    }


    /**
     * 方法调用  适合有参数的方法调用 使用时传入参数即可
     *
     * @param className  类名：精确到包名
     * @param methodName 方法名
     * @param args       参数
     */
    public static <T> T invoke(String className, String methodName, Object... args) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, convertToClazzs(args));
            return (T) method.invoke(clazz.newInstance(), args);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        throw new IllegalStateException("reflect invoke error");
    }


    /**
     * 根据参数 顺序返回对应的Class数组 <br> 例如  "James Gosling",45   则返回 java.lang.String, java.lang.Integer
     */
    private static Class<?>[] convertToClazzs(Object... args) {
        Class<?>[] classTypes = new Class<?>[args.length];
        for (int i = 0, length = args.length; i < length; i++) {
            classTypes[i] = args[i].getClass();
        }
        return classTypes;
    }
}
