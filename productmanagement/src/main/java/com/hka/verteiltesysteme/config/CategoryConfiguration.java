package com.hka.verteiltesysteme.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.categoryservice")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CategoryConfiguration {
    private String domain;
}
