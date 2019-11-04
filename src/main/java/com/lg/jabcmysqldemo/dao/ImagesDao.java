package com.lg.jabcmysqldemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.jabcmysqldemo.model.Images;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/11 15:11
 * Content:
 */
public interface ImagesDao extends BaseMapper<Images> {
    @Select("SELECT ti.`id`,ti.`name`,ti.`url` FROM `t_images` ti,`t_images_type` tit, `t_type`ty WHERE ti.`id`=tit.`i_id` AND ty.`id`=tit.`t_id` AND ty.`id`=#{id}")
    IPage<Images> getImageAndTypeId(Page<Images> page, Integer id);
}
