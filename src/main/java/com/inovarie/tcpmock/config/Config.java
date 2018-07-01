package com.inovarie.tcpmock.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Bean
    public ExecutorService executor(ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    @Bean
    public ForkJoinPool forkJoinPool() {
        return ForkJoinPool.commonPool();
    }
}
