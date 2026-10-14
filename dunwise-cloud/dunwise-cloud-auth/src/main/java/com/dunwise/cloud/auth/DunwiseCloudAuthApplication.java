package com.dunwise.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dunwise Cloud Auth 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DunwiseCloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseCloudAuthApplication.class, args);
    }
}
