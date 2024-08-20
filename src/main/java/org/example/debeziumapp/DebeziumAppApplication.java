package org.example.debeziumapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DebeziumAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DebeziumAppApplication.class, args);
    }
}
