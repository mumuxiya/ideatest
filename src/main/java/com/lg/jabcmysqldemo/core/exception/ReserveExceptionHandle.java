package com.lg.jabcmysqldemo.core.exception;

import com.lg.jabcmysqldemo.core.response.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Name: lg
 * Date: 2019/10/22 11:30
 * Content:自定义异常处理器
 */
public class ReserveExceptionHandle {

    /**
     * 处理自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ReserveException.class)
    public R handleException(ReserveException e) {
        R resp = R.error(e.getCode(), e.getMessage());
        return resp;
    }
}
