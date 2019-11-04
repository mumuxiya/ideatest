package com.lg.jabcmysqldemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lg.jabcmysqldemo.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Name: lg
 * Date: 2019/10/21 16:43
 * Content:
 */
public interface UserDao extends BaseMapper<User> {
    User getUserByName(@Param("username") String username);
}
