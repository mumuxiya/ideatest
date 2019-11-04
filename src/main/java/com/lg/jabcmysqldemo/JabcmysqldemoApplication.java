package com.lg.jabcmysqldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan({"com.lg.jabcmysqldemo.dao"})
public class JabcmysqldemoApplication {

       public static void main(String[] args) {
        SpringApplication.run(JabcmysqldemoApplication.class, args);
    }

}
