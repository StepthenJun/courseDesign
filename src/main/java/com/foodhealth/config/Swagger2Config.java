package com.foodhealth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class Swagger2Config {
//    http://localhost:9001/swagger-ui.html#/%E6%8E%A5%E5%8F%A3
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("严文骏","联系人链接","2398627868@qq.com");
        ApiInfo apiInfo = new ApiInfo(
                "每日健康",
                "Api文档",
                "v1.0",
                "",
                contact,
                "Apach 2.0 许可",
                "许可链接",
                new ArrayList<>()
        );
        return apiInfo;
    }
}
