package com.es2.infoget.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CursoDisciplina.
 */
@Entity
@Table(name = "curso_disciplina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CursoDisciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "alunos", "cursoDisciplinas" }, allowSetters = true)
    private Cursos cursos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cursoDisciplinas", "notas", "professores" }, allowSetters = true)
    private Disciplinas disciplinas;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CursoDisciplina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cursos getCursos() {
        return this.cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    public CursoDisciplina cursos(Cursos cursos) {
        this.setCursos(cursos);
        return this;
    }

    public Disciplinas getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(Disciplinas disciplinas) {
        this.disciplinas = disciplinas;
    }

    public CursoDisciplina disciplinas(Disciplinas disciplinas) {
        this.setDisciplinas(disciplinas);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CursoDisciplina)) {
            return false;
        }
        return getId() != null && getId().equals(((CursoDisciplina) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoDisciplina{" +
            "id=" + getId() +
            "}";
    }
}
