package com.lg.jabcmysqldemo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User {
    private Integer id;
    private String username;
    private String password;
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private Set<Role> roles;
}
