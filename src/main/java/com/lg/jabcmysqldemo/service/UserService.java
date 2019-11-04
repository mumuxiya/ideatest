package com.lg.jabcmysqldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.jabcmysqldemo.model.User;

/**
 * Name: lg
 * Date: 2019/10/21 16:42
 * Content:
 */
public interface UserService extends IService<User> {
    User getUserByName(String username);
    User getMapByName(String username);

    User login(User user);
}
