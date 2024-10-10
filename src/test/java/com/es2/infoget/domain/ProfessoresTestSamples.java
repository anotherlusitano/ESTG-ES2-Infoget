package com.es2.infoget.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProfessoresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Professores getProfessoresSample1() {
        return new Professores().id(1L).nomeprofessor("nomeprofessor1").email("email1").password("password1");
    }

    public static Professores getProfessoresSample2() {
        return new Professores().id(2L).nomeprofessor("nomeprofessor2").email("email2").password("password2");
    }

    public static Professores getProfessoresRandomSampleGenerator() {
        return new Professores()
            .id(longCount.incrementAndGet())
            .nomeprofessor(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}
