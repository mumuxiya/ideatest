package com.lg.jabcmysqldemo.core.realm;

import com.lg.jabcmysqldemo.model.User;
import com.lg.jabcmysqldemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Name: lg
 * Date: 2019/10/23 9:56
 * Content:
 */
@Slf4j
public class DbShiroRealm extends AuthorizingRealm {
    //数据库存储的用户密码的加密salt，正式环境不能放在源代码里
    private static final String encryptSalt = "F12839WhsnnEV$#23b";

    @Autowired
    private UserService userService;

//    public DbShiroRealm(UserService userService) {
//        this.userService = userService;
//        //因为数据库中的密码做了散列，所以使用shiro的散列Matcher
//        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
//    }
    /**
     *  找它的原因是这个方法返回true
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
    /**
     *  这一步我们根据token给的用户名，去数据库查出加密过用户密码，然后把加密后的密码和盐值一起发给shiro，让它做比对
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        log.warn("请求进来了用户验证");
//        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken)token;
//        String username = userpasswordToken.getUsername();
//        log.warn(username);
//        //UserDto user = userService.getUserInfo(username);
//        User user = userService.getUserByName(username);
//        log.warn(user.toString());
//
//
//        if(user == null)throw new AuthenticationException("用户名或者密码错误");
//
//        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(encryptSalt), "dbRealm");



        String authenticationToken = (String) token.getPrincipal();

        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken == null) {
            System.out.println("token无效");
            return null;
        }
        //获取用户信息
        String name = token.getPrincipal().toString();
        System.out.println(name);

        User user = userService.getUserByName(name);
        System.out.println(name);
        if (user == null) {
            System.out.println("用户不存在");
            //这里返回后会报出对应异常
            return null;
        }
//        if (!JwtUtil.verify(token, name, user.getPassword())){
//            System.out.println("用户名或密码错误");
//            return null;
//        }
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("请求进来了");
        return null;
    }
}