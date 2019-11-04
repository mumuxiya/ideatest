package com.lg.jabcmysqldemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name: lg
 * Date: 2019/9/21 9:54
 * Content:权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_permission")
public class Permissions {
    private Integer id;
    private String name;
    private String url;
}
