package com.hackerrank.eshopping.product.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.datasource.url","jdbc:h2:tcp://localhost:8000/mem:mydb");
        SpringApplication.run(Application.class, args);
    }
}
