package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private IdGenerator() {
    }

    public static Integer getNextId() {
        return ID_GENERATOR.incrementAndGet();
    }
}
