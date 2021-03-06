package com.lahiru.integrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/account/**"))
                .apis(RequestHandlerSelectors.basePackage("com.lahiru.integrator.web.restful"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Integration API",
                "Integration API for core banking system - Trabeya",
                "1.0",
                null,
                new Contact("Lahiru Wishwa Gunarathne",null,"lahiruwishwa10@gmail.com"),
                "Internal use only",
                "Internal use only",
                Collections.emptyList()
        );
    }
}
