package com.es2.infoget.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SecretariosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Secretarios getSecretariosSample1() {
        return new Secretarios().id(1L).nomesecretario("nomesecretario1").email("email1").password("password1");
    }

    public static Secretarios getSecretariosSample2() {
        return new Secretarios().id(2L).nomesecretario("nomesecretario2").email("email2").password("password2");
    }

    public static Secretarios getSecretariosRandomSampleGenerator() {
        return new Secretarios()
            .id(longCount.incrementAndGet())
            .nomesecretario(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}
