package com.dunwise.cloud.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dunwise Cloud AI 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DunwiseCloudAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseCloudAiApplication.class, args);
    }
}
