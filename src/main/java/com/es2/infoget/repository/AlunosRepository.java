package com.es2.infoget.repository;

import com.es2.infoget.domain.Alunos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Alunos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunosRepository extends JpaRepository<Alunos, Long> {}
