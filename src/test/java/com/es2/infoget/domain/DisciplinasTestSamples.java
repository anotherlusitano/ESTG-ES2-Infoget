package com.es2.infoget.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DisciplinasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Disciplinas getDisciplinasSample1() {
        return new Disciplinas().id(1L).nomedisciplina("nomedisciplina1").cargahoraria(1);
    }

    public static Disciplinas getDisciplinasSample2() {
        return new Disciplinas().id(2L).nomedisciplina("nomedisciplina2").cargahoraria(2);
    }

    public static Disciplinas getDisciplinasRandomSampleGenerator() {
        return new Disciplinas()
            .id(longCount.incrementAndGet())
            .nomedisciplina(UUID.randomUUID().toString())
            .cargahoraria(intCount.incrementAndGet());
    }
}
