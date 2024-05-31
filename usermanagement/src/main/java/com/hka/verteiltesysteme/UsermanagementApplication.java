package com.hka.verteiltesysteme;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UsermanagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsermanagementApplication.class, args);
    }
}
