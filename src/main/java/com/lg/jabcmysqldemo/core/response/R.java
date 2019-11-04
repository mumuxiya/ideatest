package com.lg.jabcmysqldemo.core.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应数据
 * Name: lg
 * Date: 2019/10/11 17:15
 * Content:
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1695064047622790369L;

    public static final String CODE = "code";
    public static final String MSG = "msg";

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R resp = new R();
        resp.put(MSG, msg);
        return resp;
    }

    /**
     * 操作成功,code:0
     * @param msg
     * @return
     */
    public static R pjOk(String msg) {
        R resp = new R();
        resp.put(MSG, msg);
        resp.put(CODE, 0);
        return resp;
    }

    public static R pjError(String msg) {
        R resp = new R();
        resp.put(MSG, msg);
        resp.put(CODE, 1);
        return resp;
    }

    public static R ok(Map<String, Object> map) {
        R resp = new R();
        resp.putAll(map);
        return resp;
    }

    public static R error() {
        return error(S.FAILD.code(), S.FAILD.msg());
    }

    public static R error(String msg) {
        return error(S.FAILD.code(), msg);
    }

    public static R error(int code, String msg) {
        R resp = new R();
        resp.put(CODE, code);
        resp.put(MSG, msg);
        return resp;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    private R() {
        put(CODE, S.SUCCESS.code());
        put(MSG, S.SUCCESS.msg());
    }
}
