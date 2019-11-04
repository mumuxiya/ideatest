package com.lg.jabcmysqldemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.jabcmysqldemo.dao.UserDao;
import com.lg.jabcmysqldemo.model.Permissions;
import com.lg.jabcmysqldemo.model.Role;
import com.lg.jabcmysqldemo.model.User;
import com.lg.jabcmysqldemo.tools.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Name: lg
 * Date: 2019/10/21 16:42
 * Content:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByName(String username) {


        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return userDao.getUserByName(username);
    }


    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    public User getMapByName(String userName){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Permissions permissions1 = new Permissions(1,"query","test");
        Permissions permissions2 = new Permissions(2,"add","test");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role(1,"admin",permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User(1,"admin","admin","",roleSet);
        Map<String ,User> map = new HashMap<>();
        map.put(user.getUsername(), user);
        Permissions permissions3 = new Permissions(3,"query","test");
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions3);
        Role role1 = new Role(2,"user",permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User(2,"123","123","",roleSet1);
        map.put(user1.getUsername(), user1);
        return map.get(userName);
    }

    /**
     * 验证用户名和密码
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", user.getUsername());
        User user1 = userDao.selectOne(queryWrapper);
        if(user.getPassword().equals(user1.getPassword())){
            return user1;
        }
        return null;
    }
}
