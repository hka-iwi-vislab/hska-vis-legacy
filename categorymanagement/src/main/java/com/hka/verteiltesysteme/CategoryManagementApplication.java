package com.hka.verteiltesysteme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CategoryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryManagementApplication.class, args);
    }

}
