package com.es2.infoget.repository;

import com.es2.infoget.domain.Notas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotasRepository extends JpaRepository<Notas, Long> {}
