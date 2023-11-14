package com.foodhealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = "com.foodhealth")
@MapperScan("com.foodhealth.mapper")
public class CourseDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseDesignApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
