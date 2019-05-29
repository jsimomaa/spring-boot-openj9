package com.example.application;

import java.util.concurrent.TimeUnit;

import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

/**
 * Entry point to the application. All the configuration and beans
 * defined in this package and subpackages are discovered automatically.
 */
@SpringBootApplication(scanBasePackages = {
        "com.example.application"
})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public GRpcServerBuilderConfigurer keepAliveServerConfigurer() {
        return new GRpcServerBuilderConfigurer() {
            @Override
            public void configure(ServerBuilder<?> serverBuilder) {
                if(serverBuilder instanceof NettyServerBuilder) {
                    ((NettyServerBuilder) serverBuilder)
                    .keepAliveTime(30, TimeUnit.SECONDS)
                    .keepAliveTimeout(20, TimeUnit.SECONDS)
                    .permitKeepAliveWithoutCalls(true);
                }
            }
        };
    }
}
