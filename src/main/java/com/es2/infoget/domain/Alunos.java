package com.es2.infoget.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Alunos.
 */
@Entity
@Table(name = "alunos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Alunos implements Serializable {

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
    @Column(name = "nomealuno", nullable = false)
    private String nomealuno;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alunos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alunos", "disciplinas" }, allowSetters = true)
    private Set<Notas> notas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "alunos", "cursoDisciplinas" }, allowSetters = true)
    private Cursos cursos;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alunos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomealuno() {
        return this.nomealuno;
    }

    public Alunos nomealuno(String nomealuno) {
        this.setNomealuno(nomealuno);
        return this;
    }

    public void setNomealuno(String nomealuno) {
        this.nomealuno = nomealuno;
    }

    public String getEmail() {
        return this.email;
    }

    public Alunos email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Alunos password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Notas> getNotas() {
        return this.notas;
    }

    public void setNotas(Set<Notas> notas) {
        if (this.notas != null) {
            this.notas.forEach(i -> i.setAlunos(null));
        }
        if (notas != null) {
            notas.forEach(i -> i.setAlunos(this));
        }
        this.notas = notas;
    }

    public Alunos notas(Set<Notas> notas) {
        this.setNotas(notas);
        return this;
    }

    public Alunos addNotas(Notas notas) {
        this.notas.add(notas);
        notas.setAlunos(this);
        return this;
    }

    public Alunos removeNotas(Notas notas) {
        this.notas.remove(notas);
        notas.setAlunos(null);
        return this;
    }

    public Cursos getCursos() {
        return this.cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    public Alunos cursos(Cursos cursos) {
        this.setCursos(cursos);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alunos)) {
            return false;
        }
        return getId() != null && getId().equals(((Alunos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alunos{" +
            "id=" + getId() +
            ", nomealuno='" + getNomealuno() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
