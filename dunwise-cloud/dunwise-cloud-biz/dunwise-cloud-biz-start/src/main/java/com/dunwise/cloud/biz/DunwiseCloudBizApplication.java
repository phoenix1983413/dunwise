package com.dunwise.cloud.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Dunwise Cloud Biz 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DunwiseCloudBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunwiseCloudBizApplication.class, args);
    }
}
