package com.lg.jabcmysqldemo.controller;

import com.lg.jabcmysqldemo.core.response.R;
import com.lg.jabcmysqldemo.model.User;
import com.lg.jabcmysqldemo.service.UserService;
import com.lg.jabcmysqldemo.tools.JwtUtil;
import com.lg.jabcmysqldemo.tools.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Name: lg
 * Date: 2019/10/21 16:49
 * Content:
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登入
     * @param user
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/login")
    public R login(User user, HttpServletRequest request, HttpServletResponse response) {
        log.warn("执行登录操作!");
        log.warn(user.toString());
        //获取session
        HttpSession session = request.getSession();
//        //添加用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
//        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            //subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//            return R.error("账号或密码错误！");
//        } catch (AuthorizationException e) {
//            e.printStackTrace();
//            return R.error("没有权限！");
//        }
        String sessionCode = session.getAttribute("verCode").toString();
        log.warn(sessionCode);
       if(sessionCode==null || !user.getCode().equals(sessionCode.toLowerCase())){
            return R.error("验证码有误！");
        }
//        User user1 = userService.login(user);
//        if(user1==null){
//            return R.error("账号或密码错误！");
//        }
        Subject subject = SecurityUtils.getSubject();
        try {
            log.warn("将用户请求参数封装后，直接提交给Shiro处理!");
            //将用户请求参数封装后，直接提交给Shiro处理
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
        } catch (AuthenticationException e) {
            // 如果校验失败，shiro会抛出异常，返回客户端失败
            return R.error("账号或密码错误！");
        } catch (Exception e) {
            return R.error("没有权限！");
        }
        return R.ok("登入成功！").put("user", user).put("name","admin").put("uuid", UUID.randomUUID().toString()).put("token", JwtUtil.sign(user.getUsername(),user.getPassword()));
    }

    /**
     * 验证码获取
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/code")
    public void authImage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机大写字串
        String verifyCode = ValidateCode.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession();
        // 删除以前的
        session.removeAttribute("verCode");
        session.removeAttribute("codeTime");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        session.setAttribute("codeTime", new Date());
        // 生成图片
        int w = 180, h = 38;
        OutputStream out = response.getOutputStream();
        ValidateCode.outputImage(w, h, out, verifyCode);
    }

    /**
     * 测试权限
     * @return
     */
//    @RequiresRoles("admin")
//    @RequiresPermissions("add")
    @GetMapping("/test1")
    public R test1(){
        return R.ok("权限验证通过！");
    }

    @GetMapping("/test2")
    public R test2(){
        return R.ok("权限验证通过！");
    }

    @GetMapping(value = "/notLogin")
    public R notLogin() {
        return R.error("未登陆！");
    }

    @GetMapping(value = "/notRole")
    public R notRole() {
        return R.error("没有权限！");
    }

}
