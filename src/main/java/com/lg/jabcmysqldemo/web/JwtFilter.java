package com.lg.jabcmysqldemo.web;

import com.alibaba.fastjson.JSONObject;
import com.lg.jabcmysqldemo.core.exception.ReserveException;
import com.lg.jabcmysqldemo.core.response.S;
import com.lg.jabcmysqldemo.core.token.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Name: lg
 * Date: 2019/10/22 10:56
 * Content:
 */
public class JwtFilter extends BasicHttpAuthenticationFilter implements Filter {
    /**
     * 执行登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("执行登录");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println(getIPAddress(httpServletRequest));
        System.out.println(httpServletRequest.getRequestURL());
        String token = httpServletRequest.getHeader("X-Token");
//        if(token==null){
//            throw new ReserveException(S.TOKEN_ERROR.msg(), S.TOKEN_ERROR.code());
//        }
        if(token != null){
            JwtToken jwtToken = new JwtToken(token);
            try {
                getSubject(request, response).login(jwtToken);
                // 如果没有抛出异常则代表登入成功，返回true
                return true;
            } catch (AuthenticationException e) {
                //SerializerFeature.WriteMapNullValue为了null属性也输出json的键值对
                System.out.println("没有访问权限 原因是");
                e.printStackTrace();
                Object o = JSONObject.toJSONString("没有访问权限，原因是");
                response.setCharacterEncoding("utf-8");
                response.getWriter().print(o);
                return false;
            }
        }
        return true;

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String token = httpServletRequest.getHeader("X-Token");
//        System.out.println("Token:"+token);
//        JwtToken jwtToken = new JwtToken(token);
//        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
//
//        try {
//            getSubject(request, response).login(jwtToken);
//            System.out.println("返回true");
//            // 如果没有抛出异常则代表登入成功，返回true
//            return true;
//        } catch (AuthenticationException e) {
//            //SerializerFeature.WriteMapNullValue为了null属性也输出json的键值对
//            System.out.println("没有访问权限 原因是");
//            e.printStackTrace();
//
//            Object o = JSONObject.toJSONString("没有访问权限，原因是");
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().print(o);
//            return true;
//        }

    }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("执行登录认证");
        try {
            return executeLogin(request, response);
            // return true;有一篇博客这里直接返回true是不正确的,在这里我特别指出一下
        } catch (Exception e) {
           // log.error("JwtFilter过滤验证失败!");
            return false;
        }
    }


    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("对跨域提供支持");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }*/

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
