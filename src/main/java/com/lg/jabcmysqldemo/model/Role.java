package com.lg.jabcmysqldemo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Name: lg
 * Date: 2019/9/21 9:53
 * Content:角色
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_role")
public class Role {

    private Integer id;
    private String name;
    /**
     * 角色对应权限集合
     */
    @TableField(exist = false)
    private Set<Permissions> permissionss;

}
