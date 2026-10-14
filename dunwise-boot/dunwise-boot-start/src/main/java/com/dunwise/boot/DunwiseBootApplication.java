package com.dunwise.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Dunwise Boot 启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.dunwise.boot",
        "com.dunwise.starter",
        "com.dunwise.mica"
})
public class DunwiseBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseBootApplication.class, args);
    }
}
