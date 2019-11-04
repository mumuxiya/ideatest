package com.lg.jabcmysqldemo.core.config;


import com.lg.jabcmysqldemo.core.realm.DbShiroRealm;
import com.lg.jabcmysqldemo.core.realm.ShiroRealm;
import com.lg.jabcmysqldemo.model.Permissions;
import com.lg.jabcmysqldemo.service.PermissionsService;
import com.lg.jabcmysqldemo.web.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    @Autowired
    private PermissionsService permissionsService;


    //不加这个注解不生效，具体不详
//    @Bean
//    @ConditionalOnMissingBean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
//        defaultAAP.setProxyTargetClass(true);
//        return defaultAAP;
//    }

    //将自己的验证方式加入容器
    @Bean
    public ShiroRealm myShiroRealm() {
        ShiroRealm customRealm = new ShiroRealm();
        return customRealm;
    }
    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean("dbRealm")
    public Realm dbShiroRealm() {
        DbShiroRealm myShiroRealm = new DbShiroRealm();
        return myShiroRealm;
    }

    /**
     * 用于JWT token认证的realm

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserService userService) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
        return myShiroRealm;
    }*/

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(dbShiroRealm());
        realms.add(myShiroRealm());
        //securityManager.setAuthenticator(mo);
        securityManager.setRealms(realms);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        List<Permissions> permissions = permissionsService.list();
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for(Permissions permission: permissions){
            if(permission.getName().contains("p:")){
                String p = permission.getName().replaceAll("p:","");
                map.put(permission.getUrl(), "perms["+p+"]");
            }else{
                map.put(permission.getUrl(), permission.getName());
            }
        }
        //过滤器

//        map.put("/code/**","anon");
//        map.put("/login/**","anon");
//        //登出
//        map.put("/logout", "logout");
//        map.put("/test1","perms[test1]");
        //对所有用户认证
       // map.put("/**", "authc");
        // 添加自己的过滤器并且取名为jwt
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        //map.put("/**", "jwt");
        //登录
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }



    //加入注解的使用，不加入这个注解不生效
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
}
