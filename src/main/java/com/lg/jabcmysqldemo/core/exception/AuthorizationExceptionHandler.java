package com.lg.jabcmysqldemo.core.exception;

import com.lg.jabcmysqldemo.core.response.R;
import com.lg.jabcmysqldemo.core.response.S;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Name: lg
 * Date: 2019/10/21 20:10
 * Content:处理shiro权限异常
 */
@RestControllerAdvice
public class AuthorizationExceptionHandler {
    /**
     * 处理shiro权限异常
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler({AuthorizationException.class, UnauthenticatedException.class, UnauthorizedException.class})
    public R handleAuthorizationException(AuthorizationException e) {
        return R.error(S.NON_LOGIN.code(), S.NON_LOGIN.msg());
    }
}
