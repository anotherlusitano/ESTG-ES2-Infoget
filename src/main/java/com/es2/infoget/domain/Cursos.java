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
 * A Cursos.
 */
@Entity
@Table(name = "cursos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cursos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nomecurso", nullable = false)
    private String nomecurso;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cursos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notas", "cursos" }, allowSetters = true)
    private Set<Alunos> alunos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cursos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cursos", "disciplinas" }, allowSetters = true)
    private Set<CursoDisciplina> cursoDisciplinas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cursos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomecurso() {
        return this.nomecurso;
    }

    public Cursos nomecurso(String nomecurso) {
        this.setNomecurso(nomecurso);
        return this;
    }

    public void setNomecurso(String nomecurso) {
        this.nomecurso = nomecurso;
    }

    public Set<Alunos> getAlunos() {
        return this.alunos;
    }

    public void setAlunos(Set<Alunos> alunos) {
        if (this.alunos != null) {
            this.alunos.forEach(i -> i.setCursos(null));
        }
        if (alunos != null) {
            alunos.forEach(i -> i.setCursos(this));
        }
        this.alunos = alunos;
    }

    public Cursos alunos(Set<Alunos> alunos) {
        this.setAlunos(alunos);
        return this;
    }

    public Cursos addAlunos(Alunos alunos) {
        this.alunos.add(alunos);
        alunos.setCursos(this);
        return this;
    }

    public Cursos removeAlunos(Alunos alunos) {
        this.alunos.remove(alunos);
        alunos.setCursos(null);
        return this;
    }

    public Set<CursoDisciplina> getCursoDisciplinas() {
        return this.cursoDisciplinas;
    }

    public void setCursoDisciplinas(Set<CursoDisciplina> cursoDisciplinas) {
        if (this.cursoDisciplinas != null) {
            this.cursoDisciplinas.forEach(i -> i.setCursos(null));
        }
        if (cursoDisciplinas != null) {
            cursoDisciplinas.forEach(i -> i.setCursos(this));
        }
        this.cursoDisciplinas = cursoDisciplinas;
    }

    public Cursos cursoDisciplinas(Set<CursoDisciplina> cursoDisciplinas) {
        this.setCursoDisciplinas(cursoDisciplinas);
        return this;
    }

    public Cursos addCursoDisciplina(CursoDisciplina cursoDisciplina) {
        this.cursoDisciplinas.add(cursoDisciplina);
        cursoDisciplina.setCursos(this);
        return this;
    }

    public Cursos removeCursoDisciplina(CursoDisciplina cursoDisciplina) {
        this.cursoDisciplinas.remove(cursoDisciplina);
        cursoDisciplina.setCursos(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cursos)) {
            return false;
        }
        return getId() != null && getId().equals(((Cursos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cursos{" +
            "id=" + getId() +
            ", nomecurso='" + getNomecurso() + "'" +
            "}";
    }
}
