package com.kayleh.config;

import com.kayleh.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 17:12
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    UserArgumentResolver userArgumentResolver;
    @Autowired
    AccessInterceptor accessInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(accessInterceptor);
//        interceptorRegistration.addPathPatterns("/spike/path");
    }
}
