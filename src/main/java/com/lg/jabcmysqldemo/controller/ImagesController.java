package com.lg.jabcmysqldemo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.jabcmysqldemo.core.response.R;
import com.lg.jabcmysqldemo.model.Images;
import com.lg.jabcmysqldemo.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 图片获取
 */
@RestController
@RequestMapping("/imagesApi")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping(value = "/imageAll")
    public R getImageAll(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize){
        System.out.println(pageNo);
        System.out.println(pageSize);

        Page<Images> page = new Page<Images>(pageNo, pageSize);
        IPage<Images> pages = imagesService.page(page);
        return R.ok().put("mumuxiya", pages);
    }

    @GetMapping(value = "/imageAndTypeId")
    public R getImageAndTypeId(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "id", required = false) Integer id){
        Page<Images> page = new Page<Images>(pageNo, pageSize);
        //IPage<Images> pages = imagesService.page(page);
        IPage<Images> images = imagesService.getImageAndTypeId(page, id);
        return R.ok().put("mumuxiya",images);
    }
}
