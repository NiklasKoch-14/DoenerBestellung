package com.example.doenerbestellung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DoenerBestellungApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoenerBestellungApplication.class, args);
    }

}
