package com.kwaishop.digital.access.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhangkwei
 * Created on 2022-05-13
 */
@SpringBootApplication(scanBasePackages = {"com.kwaishop.digital.access.demo"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
