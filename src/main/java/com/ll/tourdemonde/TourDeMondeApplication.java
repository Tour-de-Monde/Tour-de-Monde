package com.ll.tourdemonde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TourDeMondeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourDeMondeApplication.class, args);
    }

}
