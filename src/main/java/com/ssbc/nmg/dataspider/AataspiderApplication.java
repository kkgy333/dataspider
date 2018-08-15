package com.ssbc.nmg.dataspider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ssbc.nmg.dataspider","com.ssbc.nmg.dataspider.dao.mapper","com.ssbc.nmg.dataspider.dao","com.ssbc.nmg.dataspider.service"})
public class AataspiderApplication extends SpringBootServletInitializer {

//    public static void main(String[] args) {
//        SpringApplication.run(AataspiderApplication.class, args);
//    }



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AataspiderApplication.class);
    }
}
