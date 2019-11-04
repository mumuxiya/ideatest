package com.lg.jabcmysqldemo;


import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JabcmysqldemoApplicationTests {


    @Test
    public void contextLoads() throws IOException, MyException {

//加载配置⽂件,需要告诉它向导服务器在哪
        ClientGlobal.init("fdfs_client.conf");
                //创建客户端
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer server=null;
        StorageClient storageClient=new StorageClient(trackerServer, server);
        String[] strings = storageClient.upload_file("C:\\Users\\Administrator\\Desktop\\images\\1561974694336164.jpg",
                "jpg", null);//参数1 ⽂件路径,参数2 ⽂件的扩展名,参数3⽂件的元数据
        for (String string : strings) {
            System.err.println(string);//返回的结果就是整个地址,不包括域名 ip
        }
    }

}
