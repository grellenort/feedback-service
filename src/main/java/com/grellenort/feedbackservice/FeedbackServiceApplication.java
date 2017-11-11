package com.grellenort.feedbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = BasePackageMarker.class)
public class FeedbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedbackServiceApplication.class, args);
    }
}
