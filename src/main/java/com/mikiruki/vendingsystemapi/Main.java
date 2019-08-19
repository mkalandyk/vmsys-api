package com.mikiruki.vendingsystemapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mikiruki.vendingsystemapi.aspects")
@ComponentScan("com.mikiruki.vendingsystemapi.configs")
@ComponentScan("com.mikiruki.vendingsystemapi.controllers")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
