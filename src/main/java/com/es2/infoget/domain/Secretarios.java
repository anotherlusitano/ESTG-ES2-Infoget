package com.es2.infoget.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Secretarios.
 */
@Entity
@Table(name = "secretarios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Secretarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(
        regexp = "^[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+(?:\\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*(?:\\s(?:da|do|dos|das|[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+))?(?:\\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*$"
    )
    @Column(name = "nomesecretario", nullable = false)
    private String nomesecretario;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Secretarios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomesecretario() {
        return this.nomesecretario;
    }

    public Secretarios nomesecretario(String nomesecretario) {
        this.setNomesecretario(nomesecretario);
        return this;
    }

    public void setNomesecretario(String nomesecretario) {
        this.nomesecretario = nomesecretario;
    }

    public String getEmail() {
        return this.email;
    }

    public Secretarios email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Secretarios password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Secretarios)) {
            return false;
        }
        return getId() != null && getId().equals(((Secretarios) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Secretarios{" +
            "id=" + getId() +
            ", nomesecretario='" + getNomesecretario() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
