package com.inovarie.tcpmock.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * Custom thread factory to get fancy names for threads.
 */
@Component
public class NamedThreadFactory implements ThreadFactory {

    private final String name;
    private final AtomicInteger integer;

    public NamedThreadFactory() {
        this.name = "AppThread#";
        this.integer = new AtomicInteger(1);
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + integer.getAndIncrement());
    }

}
