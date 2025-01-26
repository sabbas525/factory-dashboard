package com.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FactoryDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(FactoryDashboardApplication.class, args);
    }
}
