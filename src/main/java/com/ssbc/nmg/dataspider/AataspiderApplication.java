package com.ssbc.nmg.dataspider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ssbc.nmg.dataspider","com.ssbc.nmg.dataspider.dao.mapper","com.ssbc.nmg.dataspider.dao"})
public class AataspiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AataspiderApplication.class, args);
    }
}