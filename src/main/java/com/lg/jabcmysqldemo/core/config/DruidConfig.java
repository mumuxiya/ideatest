package com.lg.jabcmysqldemo.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Name: lg
 * Date: 2019/10/7 11:10
 * Content:
 */

/**
 * druid
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    /**
     * 配置druid的监控
     * 配置管理后台的servlet，druid提供的这个servlet名字叫：StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean getViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        String[] urlArr = new String[]{"/druid/*"};
        bean.setUrlMappings(Arrays.asList(urlArr)); //设置对应servlet的url映射地址
        Map<String,String> initParams = new HashMap<String,String>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow",""); //设置ip白名单 不设置就是没有限制
//        initParams.put("deny","10.33.12.1");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置web监控的拦截器filter，druid提供的filter名字叫 ：WebStatFilter
     * @return
     */
    public FilterRegistrationBean getFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        //拦截那些
        bean.setUrlPatterns(Arrays.asList(new String[]{"/*"}));
        //那些不能拦截
        Map<String,String> initParams = new HashMap<String,String>();
        initParams.put("exclusions","*.js,*.css,*.jpg,*.png,/druid/*");
        bean.setInitParameters(initParams);
        return bean;
    }
}
