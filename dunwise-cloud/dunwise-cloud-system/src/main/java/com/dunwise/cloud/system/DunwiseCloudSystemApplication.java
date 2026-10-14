package com.dunwise.cloud.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dunwise Cloud System 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DunwiseCloudSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseCloudSystemApplication.class, args);
    }
}
