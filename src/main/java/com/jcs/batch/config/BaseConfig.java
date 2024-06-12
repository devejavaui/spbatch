package com.jcs.batch.config;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

    @Bean
    public ExecutionContext executionContext(){
        return new ExecutionContext();
    }
}
