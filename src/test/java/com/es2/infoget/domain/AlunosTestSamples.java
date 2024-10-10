package com.es2.infoget.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AlunosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Alunos getAlunosSample1() {
        return new Alunos().id(1L).nomealuno("nomealuno1").email("email1").password("password1");
    }

    public static Alunos getAlunosSample2() {
        return new Alunos().id(2L).nomealuno("nomealuno2").email("email2").password("password2");
    }

    public static Alunos getAlunosRandomSampleGenerator() {
        return new Alunos()
            .id(longCount.incrementAndGet())
            .nomealuno(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}
