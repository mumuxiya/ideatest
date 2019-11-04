package com.lg.jabcmysqldemo.model;

/**
 * Name: lg
 * Date: 2019/10/14 17:15
 * Content:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 播放地址
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hrefs implements Serializable {

//    /**
//     * 主键
//     */
//    private Integer id;
    /**
     * 播放器名字
     */
    private String name;
    /**
     * 播放地址
     */
    private String url;
}
