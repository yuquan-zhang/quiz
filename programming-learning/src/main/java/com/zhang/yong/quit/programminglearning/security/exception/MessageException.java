package com.zhang.yong.quit.programminglearning.security.exception;

/**
 * @author zhang yong
 * @email zhytwo@126.com
 * @date 20190102
 */
public class MessageException extends RuntimeException {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
