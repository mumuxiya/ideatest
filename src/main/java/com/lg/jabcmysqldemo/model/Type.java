package com.lg.jabcmysqldemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name: lg
 * Date: 2019/10/11 15:13
 * Content:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_type")
public class Type {
    private Integer id;
    private String name;
}
