package com.sender.sparta.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncService {
    @Async
    public void submit(Runnable runnable) {
        runnable.run();
    }
}
