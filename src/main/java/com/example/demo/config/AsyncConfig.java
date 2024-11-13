package com.example.demo.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    // 초당 5개의 요청을 처리하도록 제한
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(5.0);
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);       // 기본 스레드 수
        executor.setMaxPoolSize(3);       // 최대 스레드 수
        executor.setQueueCapacity(5);      // 큐의 최대 크기, 낮게 설정해 과부하를 유발
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}
