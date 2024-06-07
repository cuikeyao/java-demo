package com.keyao.no008springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class No008SpringRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(No008SpringRetryApplication.class, args);
    }

}
