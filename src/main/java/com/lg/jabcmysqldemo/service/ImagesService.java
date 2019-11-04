package com.lg.jabcmysqldemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.jabcmysqldemo.model.Images;

import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/11 16:49
 * Content:
 */
public interface ImagesService extends IService<Images> {

    IPage<Images> getImageAndTypeId(Page<Images> page, Integer id);
}
