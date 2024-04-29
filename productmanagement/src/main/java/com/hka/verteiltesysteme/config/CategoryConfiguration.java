package com.hka.verteiltesysteme.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.categoryservice")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryConfiguration {
    private String domain;
}
