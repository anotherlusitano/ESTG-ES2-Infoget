package com.es2.infoget.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CursoDisciplinaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CursoDisciplina getCursoDisciplinaSample1() {
        return new CursoDisciplina().id(1L);
    }

    public static CursoDisciplina getCursoDisciplinaSample2() {
        return new CursoDisciplina().id(2L);
    }

    public static CursoDisciplina getCursoDisciplinaRandomSampleGenerator() {
        return new CursoDisciplina().id(longCount.incrementAndGet());
    }
}
