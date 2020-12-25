package com.khoa.CineFlex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "frontend")
public class AppConfig {
    @NotNull
    private String url;

    public String getUrl() {
        return  url;
    }

    public void setup(String url) {
        this.url = url;
    }
}
