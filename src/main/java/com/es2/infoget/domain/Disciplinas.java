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
 * A Disciplinas.
 */
@Entity
@Table(name = "disciplinas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Disciplinas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nomedisciplina", nullable = false)
    private String nomedisciplina;

    @Min(value = 0)
    @Column(name = "cargahoraria")
    private Integer cargahoraria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disciplinas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cursos", "disciplinas" }, allowSetters = true)
    private Set<CursoDisciplina> cursoDisciplinas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disciplinas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alunos", "disciplinas" }, allowSetters = true)
    private Set<Notas> notas = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "disciplinas" }, allowSetters = true)
    private Professores professores;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Disciplinas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomedisciplina() {
        return this.nomedisciplina;
    }

    public Disciplinas nomedisciplina(String nomedisciplina) {
        this.setNomedisciplina(nomedisciplina);
        return this;
    }

    public void setNomedisciplina(String nomedisciplina) {
        this.nomedisciplina = nomedisciplina;
    }

    public Integer getCargahoraria() {
        return this.cargahoraria;
    }

    public Disciplinas cargahoraria(Integer cargahoraria) {
        this.setCargahoraria(cargahoraria);
        return this;
    }

    public void setCargahoraria(Integer cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public Set<CursoDisciplina> getCursoDisciplinas() {
        return this.cursoDisciplinas;
    }

    public void setCursoDisciplinas(Set<CursoDisciplina> cursoDisciplinas) {
        if (this.cursoDisciplinas != null) {
            this.cursoDisciplinas.forEach(i -> i.setDisciplinas(null));
        }
        if (cursoDisciplinas != null) {
            cursoDisciplinas.forEach(i -> i.setDisciplinas(this));
        }
        this.cursoDisciplinas = cursoDisciplinas;
    }

    public Disciplinas cursoDisciplinas(Set<CursoDisciplina> cursoDisciplinas) {
        this.setCursoDisciplinas(cursoDisciplinas);
        return this;
    }

    public Disciplinas addCursoDisciplina(CursoDisciplina cursoDisciplina) {
        this.cursoDisciplinas.add(cursoDisciplina);
        cursoDisciplina.setDisciplinas(this);
        return this;
    }

    public Disciplinas removeCursoDisciplina(CursoDisciplina cursoDisciplina) {
        this.cursoDisciplinas.remove(cursoDisciplina);
        cursoDisciplina.setDisciplinas(null);
        return this;
    }

    public Set<Notas> getNotas() {
        return this.notas;
    }

    public void setNotas(Set<Notas> notas) {
        if (this.notas != null) {
            this.notas.forEach(i -> i.setDisciplinas(null));
        }
        if (notas != null) {
            notas.forEach(i -> i.setDisciplinas(this));
        }
        this.notas = notas;
    }

    public Disciplinas notas(Set<Notas> notas) {
        this.setNotas(notas);
        return this;
    }

    public Disciplinas addNotas(Notas notas) {
        this.notas.add(notas);
        notas.setDisciplinas(this);
        return this;
    }

    public Disciplinas removeNotas(Notas notas) {
        this.notas.remove(notas);
        notas.setDisciplinas(null);
        return this;
    }

    public Professores getProfessores() {
        return this.professores;
    }

    public void setProfessores(Professores professores) {
        this.professores = professores;
    }

    public Disciplinas professores(Professores professores) {
        this.setProfessores(professores);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplinas)) {
            return false;
        }
        return getId() != null && getId().equals(((Disciplinas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplinas{" +
            "id=" + getId() +
            ", nomedisciplina='" + getNomedisciplina() + "'" +
            ", cargahoraria=" + getCargahoraria() +
            "}";
    }
}
