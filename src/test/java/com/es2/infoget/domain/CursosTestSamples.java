package com.es2.infoget.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CursosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cursos getCursosSample1() {
        return new Cursos().id(1L).nomecurso("nomecurso1");
    }

    public static Cursos getCursosSample2() {
        return new Cursos().id(2L).nomecurso("nomecurso2");
    }

    public static Cursos getCursosRandomSampleGenerator() {
        return new Cursos().id(longCount.incrementAndGet()).nomecurso(UUID.randomUUID().toString());
    }
}
