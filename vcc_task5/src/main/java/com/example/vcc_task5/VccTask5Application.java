package com.example.vcc_task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VccTask5Application {

    public static void main(String[] args) {
        SpringApplication.run(VccTask5Application.class, args);
        RandomDataTask6.generateData(5000000, "user");
        RandomDataTask6.startWith("h", "user");
        RandomDataTask6.containWith("h", "user");
        RandomDataTask6.withName("PICoUWC", "user");
    }

}
