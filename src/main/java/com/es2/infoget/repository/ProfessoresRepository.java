package com.es2.infoget.repository;

import com.es2.infoget.domain.Professores;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Professores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessoresRepository extends JpaRepository<Professores, Long> {}
