package com.lg.jabcmysqldemo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_images")
public class Images {
    private Integer id;
    private String name;
    private String url;
}
