package com.es2.infoget.domain;

import static com.es2.infoget.domain.SecretariosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecretariosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Secretarios.class);
        Secretarios secretarios1 = getSecretariosSample1();
        Secretarios secretarios2 = new Secretarios();
        assertThat(secretarios1).isNotEqualTo(secretarios2);

        secretarios2.setId(secretarios1.getId());
        assertThat(secretarios1).isEqualTo(secretarios2);

        secretarios2 = getSecretariosSample2();
        assertThat(secretarios1).isNotEqualTo(secretarios2);
    }
}
