package com.dealsdelta.scheduleme.config;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */

@org.springframework.context.annotation.Configuration
@EnableScheduling
@EnableWebMvc
public class Configuration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST","DELETE", "PUT", "PATCH");
    }
}