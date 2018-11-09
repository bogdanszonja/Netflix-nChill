package com.codecool.netflixandchill;

import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApplicationStart {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonParser jsonParser() {
        return new JsonParser();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }

}
