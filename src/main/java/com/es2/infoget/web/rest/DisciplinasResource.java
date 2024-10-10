package com.es2.infoget.web.rest;

import com.es2.infoget.domain.Disciplinas;
import com.es2.infoget.repository.DisciplinasRepository;
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
 * REST controller for managing {@link com.es2.infoget.domain.Disciplinas}.
 */
@RestController
@RequestMapping("/api/disciplinas")
@Transactional
public class DisciplinasResource {

    private static final Logger LOG = LoggerFactory.getLogger(DisciplinasResource.class);

    private static final String ENTITY_NAME = "disciplinas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinasRepository disciplinasRepository;

    public DisciplinasResource(DisciplinasRepository disciplinasRepository) {
        this.disciplinasRepository = disciplinasRepository;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplinas.
     *
     * @param disciplinas the disciplinas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinas, or with status {@code 400 (Bad Request)} if the disciplinas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Disciplinas> createDisciplinas(@Valid @RequestBody Disciplinas disciplinas) throws URISyntaxException {
        LOG.debug("REST request to save Disciplinas : {}", disciplinas);
        if (disciplinas.getId() != null) {
            throw new BadRequestAlertException("A new disciplinas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        disciplinas = disciplinasRepository.save(disciplinas);
        return ResponseEntity.created(new URI("/api/disciplinas/" + disciplinas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, disciplinas.getId().toString()))
            .body(disciplinas);
    }

    /**
     * {@code PUT  /disciplinas/:id} : Updates an existing disciplinas.
     *
     * @param id the id of the disciplinas to save.
     * @param disciplinas the disciplinas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinas,
     * or with status {@code 400 (Bad Request)} if the disciplinas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Disciplinas> updateDisciplinas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Disciplinas disciplinas
    ) throws URISyntaxException {
        LOG.debug("REST request to update Disciplinas : {}, {}", id, disciplinas);
        if (disciplinas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        disciplinas = disciplinasRepository.save(disciplinas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinas.getId().toString()))
            .body(disciplinas);
    }

    /**
     * {@code PATCH  /disciplinas/:id} : Partial updates given fields of an existing disciplinas, field will ignore if it is null
     *
     * @param id the id of the disciplinas to save.
     * @param disciplinas the disciplinas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinas,
     * or with status {@code 400 (Bad Request)} if the disciplinas is not valid,
     * or with status {@code 404 (Not Found)} if the disciplinas is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplinas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disciplinas> partialUpdateDisciplinas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Disciplinas disciplinas
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Disciplinas partially : {}, {}", id, disciplinas);
        if (disciplinas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disciplinas> result = disciplinasRepository
            .findById(disciplinas.getId())
            .map(existingDisciplinas -> {
                if (disciplinas.getNomedisciplina() != null) {
                    existingDisciplinas.setNomedisciplina(disciplinas.getNomedisciplina());
                }
                if (disciplinas.getCargahoraria() != null) {
                    existingDisciplinas.setCargahoraria(disciplinas.getCargahoraria());
                }

                return existingDisciplinas;
            })
            .map(disciplinasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinas.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("")
    public List<Disciplinas> getAllDisciplinas() {
        LOG.debug("REST request to get all Disciplinas");
        return disciplinasRepository.findAll();
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplinas.
     *
     * @param id the id of the disciplinas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Disciplinas> getDisciplinas(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Disciplinas : {}", id);
        Optional<Disciplinas> disciplinas = disciplinasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disciplinas);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplinas.
     *
     * @param id the id of the disciplinas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplinas(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Disciplinas : {}", id);
        disciplinasRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
