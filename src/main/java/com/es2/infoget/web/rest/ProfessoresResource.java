package com.es2.infoget.web.rest;

import com.es2.infoget.domain.Professores;
import com.es2.infoget.repository.ProfessoresRepository;
import com.es2.infoget.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.es2.infoget.domain.Professores}.
 */
@RestController
@RequestMapping("/api/professores")
@Transactional
public class ProfessoresResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProfessoresResource.class);

    private static final String ENTITY_NAME = "professores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessoresRepository professoresRepository;

    public ProfessoresResource(ProfessoresRepository professoresRepository) {
        this.professoresRepository = professoresRepository;
    }

    /**
     * {@code POST  /professores} : Create a new professores.
     *
     * @param professores the professores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professores, or with status {@code 400 (Bad Request)} if the professores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Professores> createProfessores(@Valid @RequestBody Professores professores) throws URISyntaxException {
        LOG.debug("REST request to save Professores : {}", professores);
        if (professores.getId() != null) {
            throw new BadRequestAlertException("A new professores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        professores = professoresRepository.save(professores);
        return ResponseEntity.created(new URI("/api/professores/" + professores.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, professores.getId().toString()))
            .body(professores);
    }

    /**
     * {@code PUT  /professores/:id} : Updates an existing professores.
     *
     * @param id the id of the professores to save.
     * @param professores the professores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professores,
     * or with status {@code 400 (Bad Request)} if the professores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Professores> updateProfessores(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Professores professores
    ) throws URISyntaxException {
        LOG.debug("REST request to update Professores : {}, {}", id, professores);
        if (professores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        professores = professoresRepository.save(professores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professores.getId().toString()))
            .body(professores);
    }

    /**
     * {@code PATCH  /professores/:id} : Partial updates given fields of an existing professores, field will ignore if it is null
     *
     * @param id the id of the professores to save.
     * @param professores the professores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professores,
     * or with status {@code 400 (Bad Request)} if the professores is not valid,
     * or with status {@code 404 (Not Found)} if the professores is not found,
     * or with status {@code 500 (Internal Server Error)} if the professores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Professores> partialUpdateProfessores(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Professores professores
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Professores partially : {}, {}", id, professores);
        if (professores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, professores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!professoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Professores> result = professoresRepository
            .findById(professores.getId())
            .map(existingProfessores -> {
                if (professores.getNomeprofessor() != null) {
                    existingProfessores.setNomeprofessor(professores.getNomeprofessor());
                }
                if (professores.getEmail() != null) {
                    existingProfessores.setEmail(professores.getEmail());
                }
                if (professores.getPassword() != null) {
                    existingProfessores.setPassword(professores.getPassword());
                }

                return existingProfessores;
            })
            .map(professoresRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professores.getId().toString())
        );
    }

    /**
     * {@code GET  /professores} : get all the professores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professores in body.
     */
    @GetMapping("")
    public List<Professores> getAllProfessores() {
        LOG.debug("REST request to get all Professores");
        return professoresRepository.findAll();
    }

    /**
     * {@code GET  /professores/:id} : get the "id" professores.
     *
     * @param id the id of the professores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Professores> getProfessores(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Professores : {}", id);
        Optional<Professores> professores = professoresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(professores);
    }

    /**
     * {@code DELETE  /professores/:id} : delete the "id" professores.
     *
     * @param id the id of the professores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessores(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Professores : {}", id);
        professoresRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
