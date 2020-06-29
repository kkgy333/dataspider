package com.ssbc.nmg.dataspider.interceptor;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor());
        // 拦截路径
        loginRegistry.addPathPatterns("/api/start");
        loginRegistry.addPathPatterns("/api/count");
        loginRegistry.addPathPatterns("/api/getAgencyList");
        loginRegistry.addPathPatterns("/api/extractingAgency");
        loginRegistry.addPathPatterns("/api/getExtractingLogList");
        loginRegistry.addPathPatterns("/api/deleteExtractingLog");
        loginRegistry.addPathPatterns("/api/random");
    }

    // 注册拦截器
    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}