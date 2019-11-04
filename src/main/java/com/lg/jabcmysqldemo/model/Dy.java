package com.lg.jabcmysqldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/8 12:32
 * Content:
 */

/**
 * 电影信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dy implements Serializable {
    /**
     * 影片名字
     */
    private String name;
    /**
     * 详情页地址
     */
    private String url;
    /**
     * 初始播放地址
     */
    private String onePlay;
    /**
     * 封面地址
     */
    private String imgUrl;
    /**
     * 评分
     */
    private String score;
    /**
     * 演员
     */
    private String actor;
    /**
     * 分类
     */
    private String type;
    /**
     * 导演
     */
    private String director;
    /**
     * 地区
     */
    private String region;
    /**
     * 年份
     */
    private String year;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 播放地址
     */
    private List<Hrefs> hrefs = new ArrayList<Hrefs>();
    /**
     * 猜你喜欢
     */
    private List<Dy> dys = new ArrayList<Dy>();
}

