package com.yydscm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chenzhaopeng on 2017/6/20.
 */

/**
 * 自定义拦截器配置
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    UserSecurityInterceptor userSecurityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userSecurityInterceptor);
    }

}
