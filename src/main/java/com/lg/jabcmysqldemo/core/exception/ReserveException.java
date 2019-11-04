package com.lg.jabcmysqldemo.core.exception;

import com.lg.jabcmysqldemo.core.response.S;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name: lg
 * Date: 2019/10/22 11:30
 * Content:
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReserveException extends RuntimeException {
    private String msg = S.SERVICE_BAD.msg();
    private int code = S.SERVICE_BAD.code();

    public ReserveException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ReserveException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ReserveException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ReserveException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
