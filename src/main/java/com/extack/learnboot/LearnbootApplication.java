package com.extack.learnboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.extack.learnboot.repository")
@ComponentScan(basePackages = { "com.extack.learnboot.model","com.extack.learnboot.security","com.extack.learnboot.repository","com.extack.learnboot.controller" })
@EntityScan("com.extack.learnboot.model")
@SpringBootApplication
public class LearnbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnbootApplication.class, args);
    }

}
