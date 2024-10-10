package com.es2.infoget.repository;

import com.es2.infoget.domain.CursoDisciplina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CursoDisciplina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursoDisciplinaRepository extends JpaRepository<CursoDisciplina, Long> {}
