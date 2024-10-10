package com.es2.infoget.repository;

import com.es2.infoget.domain.Disciplinas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Disciplinas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplinasRepository extends JpaRepository<Disciplinas, Long> {}
