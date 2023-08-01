package com.momotalk_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;

@Configuration
public class MissionConfig {
    @Bean
    public Timer timer(){
        return new Timer("globalTimer");
    }
}
