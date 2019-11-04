package com.lg.jabcmysqldemo.core.response;

import java.util.HashMap;

/**
 * Name: lg
 * Date: 2019/10/11 17:16
 * Content:
 */
public enum S {

    /** 操作失败 **/
    FAILD(1, "操作失败"),

    /** 操作成功 **/
    SUCCESS(0, "操作成功"),

    /** 请求超时 **/
    TIME_OUT(300, "请求超时"),

    /** 没有权限 **/
    NON_PERMS(400, "没有权限"),

    /** 未授权登入 **/
    NON_LOGIN(401, "未授权登入"),

    /** 服务异常 **/
    SERVICE_BAD(500, "服务异常"),

    /** 会话超时 **/
    SESSION_TIMEOUT(601, "会话超时"),

    /** 用户名错误 **/
    ACCOUNT_ERROR(602, "用户名或密码不正确"),

    /** 密码错误 **/
    PASSWORD_ERROR(603, "密码不正确"),

    /** 账户已锁定 **/
    ACCOUNT_LOCKED(604, "账户已锁定"),

    /** 账户未启用 **/
    ACCOUNT_DISABLE(605, "账号未启用"),

    /** 令牌解析失败 **/
    TOKEN_ERROR(606, "令牌解析失败"),

    /** 手机号码不存在 **/
    MOBILE_ERROR(701, "手机号码不存在"),

    /** 验证码不正确 **/
    VCODE_ERROR(702, "验证码不正确"),

    /** 验证码已失效 **/
    VCODE_EXPIRED(703, "验证码已失效"),

    /** 登陆码不正确 **/
    LOGIN_NUMBER(704, "登陆码不正确"),

    ;

    private int code;
    private String msg;

    public String msg() {
        return msg;
    }

    public int code() {
        return code;
    }

    S(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
