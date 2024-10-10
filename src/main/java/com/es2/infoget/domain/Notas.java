package com.es2.infoget.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notas.
 */
@Entity
@Table(name = "notas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Min(value = 0)
    @Max(value = 20)
    @Column(name = "nota")
    private Integer nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "notas", "cursos" }, allowSetters = true)
    private Alunos alunos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cursoDisciplinas", "notas", "professores" }, allowSetters = true)
    private Disciplinas disciplinas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return this.nota;
    }

    public Notas nota(Integer nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Alunos getAlunos() {
        return this.alunos;
    }

    public void setAlunos(Alunos alunos) {
        this.alunos = alunos;
    }

    public Notas alunos(Alunos alunos) {
        this.setAlunos(alunos);
        return this;
    }

    public Disciplinas getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(Disciplinas disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Notas disciplinas(Disciplinas disciplinas) {
        this.setDisciplinas(disciplinas);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notas)) {
            return false;
        }
        return getId() != null && getId().equals(((Notas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notas{" +
            "id=" + getId() +
            ", nota=" + getNota() +
            "}";
    }
}
