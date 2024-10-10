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
 * A Professores.
 */
@Entity
@Table(name = "professores")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Professores implements Serializable {

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
    @Column(name = "nomeprofessor", nullable = false)
    private String nomeprofessor;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professores")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cursoDisciplinas", "notas", "professores" }, allowSetters = true)
    private Set<Disciplinas> disciplinas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professores id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeprofessor() {
        return this.nomeprofessor;
    }

    public Professores nomeprofessor(String nomeprofessor) {
        this.setNomeprofessor(nomeprofessor);
        return this;
    }

    public void setNomeprofessor(String nomeprofessor) {
        this.nomeprofessor = nomeprofessor;
    }

    public String getEmail() {
        return this.email;
    }

    public Professores email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Professores password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Disciplinas> getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(Set<Disciplinas> disciplinas) {
        if (this.disciplinas != null) {
            this.disciplinas.forEach(i -> i.setProfessores(null));
        }
        if (disciplinas != null) {
            disciplinas.forEach(i -> i.setProfessores(this));
        }
        this.disciplinas = disciplinas;
    }

    public Professores disciplinas(Set<Disciplinas> disciplinas) {
        this.setDisciplinas(disciplinas);
        return this;
    }

    public Professores addDisciplinas(Disciplinas disciplinas) {
        this.disciplinas.add(disciplinas);
        disciplinas.setProfessores(this);
        return this;
    }

    public Professores removeDisciplinas(Disciplinas disciplinas) {
        this.disciplinas.remove(disciplinas);
        disciplinas.setProfessores(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professores)) {
            return false;
        }
        return getId() != null && getId().equals(((Professores) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professores{" +
            "id=" + getId() +
            ", nomeprofessor='" + getNomeprofessor() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
