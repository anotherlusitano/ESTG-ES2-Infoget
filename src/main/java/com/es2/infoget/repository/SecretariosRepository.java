package com.es2.infoget.repository;

import com.es2.infoget.domain.Secretarios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Secretarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecretariosRepository extends JpaRepository<Secretarios, Long> {}
