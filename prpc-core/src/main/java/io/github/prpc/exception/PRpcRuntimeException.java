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
package io.github.prpc.exception;

/**
 * 运行时异常
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class PRpcRuntimeException extends RuntimeException {


    private ResultCodeEnum resultCodeEnum;

    public PRpcRuntimeException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getName());
    }

    public PRpcRuntimeException(ResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum + ":" + message);
    }

    public PRpcRuntimeException() {
    }

    public PRpcRuntimeException(String message) {
        super(message);
    }

    public PRpcRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PRpcRuntimeException(Throwable cause) {
        super(cause);
    }

    public PRpcRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }
}
