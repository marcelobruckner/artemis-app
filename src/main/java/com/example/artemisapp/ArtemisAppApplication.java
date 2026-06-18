package com.example.artemisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class ArtemisAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisAppApplication.class, args);
    }
}
