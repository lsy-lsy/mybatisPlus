package com.mybatisplus.demo.config.shiro;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

//    创建shirofilterFactroyBean

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securtiyManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterChaiDefintionMap = new LinkedHashMap<>();
//        静态资源
        filterChaiDefintionMap.put("/css/**","anon");
        filterChaiDefintionMap.put("/fonts/**","anon");
        filterChaiDefintionMap.put("/static/**","anon");
        filterChaiDefintionMap.put("/images/**","anon");
        filterChaiDefintionMap.put("/js/**","anon");
        filterChaiDefintionMap.put("/layui/**","anon");
        filterChaiDefintionMap.put("/**/**save**","anon");
        filterChaiDefintionMap.put("/icheck/**","anon");
        filterChaiDefintionMap.put("/index/**","anon");
        filterChaiDefintionMap.put("/admin/*Login","anon");

        filterChaiDefintionMap.put("/teacherInd/**","anon");
        filterChaiDefintionMap.put("/**/upload*","anon");

        //配置退出 过滤器
        filterChaiDefintionMap.put("/logout","logout");
        filterChaiDefintionMap.put("/**","authc");
        //登陆页面
        shiroFilterFactoryBean.setLoginUrl("/admin/adminLogin");
        //成功跳转的页面
//        shiroFilterFactoryBean.setLoginUrl("/admin/index");
        //为授权登陆界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChaiDefintionMap);

        return shiroFilterFactoryBean;
    }

//    设置session过期时间
@Bean(name="sessionManager")
    public DefaultSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间
        sessionManager.setGlobalSessionTimeout(360000L);
        return sessionManager;
    }
    //创建DefaultWebSecurityManager
    @Bean(name="securtiyManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("shiroRealm")ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //关联realm
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }








}
