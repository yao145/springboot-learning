package com.cjw.springbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringbootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStarterApplication.class, args);
    }

}

