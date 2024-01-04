/*
This code comes from the jvm-sandbox-repeater(link:https://github.com/alibaba/jvm-sandbox-repeater) project.
 */
package com.alibaba.jvm.sandbox.repeater.plugin.exception;

/**
 * {@link RepeatException} 回放异常
 * <p>
 *
 * @author zhaoyb1990
 */
public class RepeatException extends BaseException {

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}