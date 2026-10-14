package com.dunwise.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dunwise Cloud Gateway 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DunwiseCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseCloudGatewayApplication.class, args);
    }
}
