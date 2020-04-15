package com.wawrze.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LibraryApplication.class);
        app.setAdditionalProfiles("run");
        app.run(args);
    }

}