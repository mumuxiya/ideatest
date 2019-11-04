package com.lg.jabcmysqldemo.core.realm;


import com.lg.jabcmysqldemo.core.token.JwtToken;
import com.lg.jabcmysqldemo.model.Permissions;
import com.lg.jabcmysqldemo.model.Role;
import com.lg.jabcmysqldemo.model.User;
import com.lg.jabcmysqldemo.service.UserService;
import com.lg.jabcmysqldemo.tools.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限");
        String username = JwtUtil.getUsername(principalCollection.toString());
        User user = userService.getUserByName(username);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            //添加权限
            for (Permissions permission : role.getPermissionss()) {
                String name = permission.getName();
                if(name.contains("p:")){
                    name = name.replaceAll("p:","");
                    simpleAuthorizationInfo.addStringPermission(name);
                }else{
                    simpleAuthorizationInfo.addStringPermission(name);
                }
            }
        }
        return simpleAuthorizationInfo;
        //获取登录用户名
//        String name = (String) principalCollection.getPrimaryPrincipal();
//        System.out.println("权限"+name);
//        //根据用户名去数据库查询用户信息
//        User user = userService.getUserByName(name);
//        //添加角色和权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        for (Role role : user.getRoles()) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            //添加权限
//            for (Permissions permissions : role.getPermissions()) {
//                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
//            }
//        }
//        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("密码验证");

        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = null;
        try {
            //这里工具类没有处理空指针等异常这里处理一下(这里处理科学一些)
            username = JwtUtil.getUsername(token);
        } catch (Exception e) {
            throw new AuthenticationException("heard的token拼写错误或者值为空");
        }
        if (username == null) {
            //log.error("token无效(空''或者null都不行!)");
            throw new AuthenticationException("token无效");
        }
        User userBean = userService.getUserByName(username);
        System.out.println("USER"+userBean);
        if (userBean == null) {
            //log.error("用户不存在!)");
            throw new AuthenticationException("用户不存在!");
        }

        if (!JwtUtil.verify(token, username, userBean.getPassword())) {
            //log.error("用户名或密码错误(token无效或者与登录者不匹配)!)");
            throw new AuthenticationException("用户名或密码错误(token无效或者与登录者不匹配)!");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
//        String token = (String) authenticationToken.getPrincipal();
//
//        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
//        if (token == null) {
//            System.out.println("token无效");
//            return null;
//        }
//        //获取用户信息
//        String name = authenticationToken.getPrincipal().toString();
//        User user = userService.getUserByName(name);
//        if (user == null) {
//            System.out.println("用户不存在");
//            //这里返回后会报出对应异常
//            return null;
//        }
////        if (!JwtUtil.verify(token, name, user.getPassword())){
////            System.out.println("用户名或密码错误");
////            return null;
////        }
//            //这里验证authenticationToken和simpleAuthenticationInfo的信息
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
//            return simpleAuthenticationInfo;

    }
}
