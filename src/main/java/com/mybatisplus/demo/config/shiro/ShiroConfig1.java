//package com.mybatisplus.demo.config.shiro;
//
//import net.sf.ehcache.CacheManager;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
//import org.apache.shiro.codec.Base64;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.CookieRememberMeManager;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private String port;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${video.isRedisSession}")
//    private boolean isRedisSession;
//
//
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
//        System.out.println("shiroConfiguration.shiroFilter()");
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        //拦截器
//        Map<String,String> filterChaiDefintionMap = new LinkedHashMap<String, String>();
////        静态资源
//        filterChaiDefintionMap.put("/css/**","anon");
//        filterChaiDefintionMap.put("/fonts/**","anon");
//        filterChaiDefintionMap.put("/images/**","anon");
//        filterChaiDefintionMap.put("/js/**","anon");
//        filterChaiDefintionMap.put("/layui/**","anon");
//        filterChaiDefintionMap.put("/icheck/**","anon");
//        //配置退出 过滤器
//        filterChaiDefintionMap.put("/logout","logout");
//        filterChaiDefintionMap.put("/**","authc");
//        //登陆页面
//        shiroFilterFactoryBean.setLoginUrl("/admin/adminLogin");
//        //登陆成功跳转的页面
//        shiroFilterFactoryBean.setLoginUrl("/admin/index");
//        //为授权登陆界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/403");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChaiDefintionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    //shiro安全管理器
//    @Bean
//    public SecurityManager securityManager(EhCacheManager ehCacheManager){
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(shiroRealm());
//        if(isRedisSession){
//            //定义缓存实现 使用redis
//            securityManager.setCacheManager(cacheManager());
//
////            自定义session管理
//            securityManager.setSessionManager(sessionManager());
//        }else{
//            securityManager.setCacheManager(ehCacheManager);
//        }
//        //注入记住我管理器
//        securityManager.setRememberMeManager(rememberMeManager());
//        return securityManager;
//    }
//
//    /**
//     * Realm
//     * @return
//     */
//    @Bean
//    public ShiroRealm shiroRealm(){
//        return new ShiroRealm();
//    }
//
//
//    //记住我 cookie
//    public SimpleCookie remenberMeCookie(){
////        参数是cookie的名称对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//
////        记住我cookie生效时间30天，单位秒
//        simpleCookie.setMaxAge(2592000);
//        return simpleCookie;
//    }
//    /***
//     * cookie管理对象；记住我功能
//     */
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(remenberMeCookie());
//        //rememberMe cookie加密的密钥
//        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
//        return cookieRememberMeManager;
//    }
//
//
//
//    /**
//     * 配置shiro redisManager
//     */
//    public RedisManager redisManager(){
//        RedisManager redisManager= new RedisManager();
//        redisManager.setHost(host);
//        if(StringUtils.isNoneBlank(password)){
//            redisManager.setPassword(password);
//        }
//        return redisManager;
//    }
//    //    redis cacheManager 缓存
////    @Bean
//    public RedisCacheManager cacheManager(){
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
//
//
////    RedisSessionDAO Shiro
//    @Bean
//    @ConditionalOnProperty(name="video.isRedisSession",havingValue ="true" )
//    public RedisSessionDAO redisSessionDAO(){
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//
//    }
//
//    /**
//     * shiro Session管理
//     * @return
//     */
//    @Bean
//    @ConditionalOnProperty(name="video.isRedisSession",havingValue ="true")
//    public DefaultWebSessionManager sessionManager(){
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        return sessionManager;
//    }
//
//    /**
//     * 单片机下的cache
//     * @param cacheManager
//     * @return
//     */
//    @Bean
//    @ConditionalOnProperty(name = "ins1st.isRedisSession", havingValue = "false")
//    public EhCacheManager ehCacheManager(CacheManager cacheManager) {
//        EhCacheManager em = new EhCacheManager();
//        em.setCacheManager(cacheManager);
//        return em;
//    }
//
//    /**
//     * 自定义登入过滤器
//     * @return
//     */
//    @Bean
//    public ShiroLoginFilter shiroLoginFilter(){
//       return new ShiroLoginFilter();
//    }
//}
