package com.dunwise.mica.core.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础业务异常
 */
public class DunwiseException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int code;

    public DunwiseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DunwiseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DunwiseException(String message) {
        super(message);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }
}
