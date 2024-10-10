package com.es2.infoget.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NotasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Notas getNotasSample1() {
        return new Notas().id(1L).nota(1);
    }

    public static Notas getNotasSample2() {
        return new Notas().id(2L).nota(2);
    }

    public static Notas getNotasRandomSampleGenerator() {
        return new Notas().id(longCount.incrementAndGet()).nota(intCount.incrementAndGet());
    }
}
