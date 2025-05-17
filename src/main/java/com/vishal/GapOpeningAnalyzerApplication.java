package com.vishal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GapOpeningAnalyzerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GapOpeningAnalyzerApplication.class, args);
    }
}