package com.lg.jabcmysqldemo.tools;

/**
 * Name: lg
 * Date: 2019/10/10 17:02
 * Content:
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 阿里云文件上传
 */
public class FileTool {
    /**
     * 上传文件
     * @param inputStream 文件
     * @param name 文件名
     * @throws FileNotFoundException
     */
    public static void file(InputStream inputStream, String name) throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIYbAbvD3iJFMd";
        String accessKeySecret = "zyYyjUM8O4gl2WsEbkozsrd55AJLcS";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        ossClient.putObject("mumuxiya", name, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
