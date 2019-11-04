package com.lg.jabcmysqldemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.jabcmysqldemo.dao.ImagesDao;
import com.lg.jabcmysqldemo.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Name: lg
 * Date: 2019/10/11 16:51
 * Content:
 */
@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesDao, Images> implements ImagesService {

    @Autowired
    private ImagesDao imagesDao;


    @Override
    public IPage<Images> getImageAndTypeId(Page<Images> page, Integer id) {
        return imagesDao.getImageAndTypeId(page, id);
    }
}
