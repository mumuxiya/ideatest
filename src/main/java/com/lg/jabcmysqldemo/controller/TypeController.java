package com.lg.jabcmysqldemo.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lg.jabcmysqldemo.core.response.R;
import com.lg.jabcmysqldemo.model.Type;
import com.lg.jabcmysqldemo.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Name: lg
 * Date: 2019/10/11 17:24
 * Content:
 */
@RestController
@RequestMapping("/typeApi")
@Slf4j
public class TypeController {


    @Autowired
    private TypeService typeService;
    @Autowired

    @GetMapping(value = "/typeAll")
    public R getTypeAll(){
        List<Type> types = typeService.list();
        return R.ok().put("mumuxiya",types);
    }
    @PostMapping("/singlefile")
    public R singleFileUpload(MultipartFile file) {
        System.out.println(file);
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIYbAbvD3iJFMd";
        String accessKeySecret = "zyYyjUM8O4gl2WsEbkozsrd55AJLcS";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        ossClient.putObject("mumuxiya", UUID.randomUUID().toString(), (File) file);
        // 关闭OSSClient。
        ossClient.shutdown();
        return R.ok();
}

}
