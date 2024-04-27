package com.hka.verteiltesysteme.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("services")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Configuration
public class Config {
    public String products;
}
