package com.lg.jabcmysqldemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lg.jabcmysqldemo.model.Role;
import com.lg.jabcmysqldemo.model.Type;

/**
 * Name: lg
 * Date: 2019/10/22 16:54
 * Content:
 */
public interface RoleDao extends BaseMapper<Role> {
    Role selectByUserId(Integer id);
}
