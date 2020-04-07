package com.mybatisplus.demo.handler;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ResourceHandler implements WebMvcConfigurer {
    //解决spring-boot缓存静态资源问题
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static")
                .setCachePeriod(31556926);//表示缓存的时间（秒）
    }

}
