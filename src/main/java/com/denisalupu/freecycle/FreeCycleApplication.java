package com.denisalupu.freecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class FreeCycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeCycleApplication.class, args);
    }

}
