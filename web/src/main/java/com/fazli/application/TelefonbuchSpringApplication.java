package com.fazli.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fazli")
public class TelefonbuchSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelefonbuchSpringApplication.class, args);
    }

}
