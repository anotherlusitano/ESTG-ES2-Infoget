package com.es2.infoget.web.rest;

import com.es2.infoget.domain.Cursos;
import com.es2.infoget.repository.CursosRepository;
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
 * REST controller for managing {@link com.es2.infoget.domain.Cursos}.
 */
@RestController
@RequestMapping("/api/cursos")
@Transactional
public class CursosResource {

    private static final Logger LOG = LoggerFactory.getLogger(CursosResource.class);

    private static final String ENTITY_NAME = "cursos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CursosRepository cursosRepository;

    public CursosResource(CursosRepository cursosRepository) {
        this.cursosRepository = cursosRepository;
    }

    /**
     * {@code POST  /cursos} : Create a new cursos.
     *
     * @param cursos the cursos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cursos, or with status {@code 400 (Bad Request)} if the cursos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cursos> createCursos(@Valid @RequestBody Cursos cursos) throws URISyntaxException {
        LOG.debug("REST request to save Cursos : {}", cursos);
        if (cursos.getId() != null) {
            throw new BadRequestAlertException("A new cursos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cursos = cursosRepository.save(cursos);
        return ResponseEntity.created(new URI("/api/cursos/" + cursos.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cursos.getId().toString()))
            .body(cursos);
    }

    /**
     * {@code PUT  /cursos/:id} : Updates an existing cursos.
     *
     * @param id the id of the cursos to save.
     * @param cursos the cursos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursos,
     * or with status {@code 400 (Bad Request)} if the cursos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cursos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cursos> updateCursos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Cursos cursos
    ) throws URISyntaxException {
        LOG.debug("REST request to update Cursos : {}, {}", id, cursos);
        if (cursos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cursos = cursosRepository.save(cursos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cursos.getId().toString()))
            .body(cursos);
    }

    /**
     * {@code PATCH  /cursos/:id} : Partial updates given fields of an existing cursos, field will ignore if it is null
     *
     * @param id the id of the cursos to save.
     * @param cursos the cursos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursos,
     * or with status {@code 400 (Bad Request)} if the cursos is not valid,
     * or with status {@code 404 (Not Found)} if the cursos is not found,
     * or with status {@code 500 (Internal Server Error)} if the cursos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cursos> partialUpdateCursos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Cursos cursos
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Cursos partially : {}, {}", id, cursos);
        if (cursos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cursos> result = cursosRepository
            .findById(cursos.getId())
            .map(existingCursos -> {
                if (cursos.getNomecurso() != null) {
                    existingCursos.setNomecurso(cursos.getNomecurso());
                }

                return existingCursos;
            })
            .map(cursosRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cursos.getId().toString())
        );
    }

    /**
     * {@code GET  /cursos} : get all the cursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cursos in body.
     */
    @GetMapping("")
    public List<Cursos> getAllCursos() {
        LOG.debug("REST request to get all Cursos");
        return cursosRepository.findAll();
    }

    /**
     * {@code GET  /cursos/:id} : get the "id" cursos.
     *
     * @param id the id of the cursos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cursos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cursos> getCursos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Cursos : {}", id);
        Optional<Cursos> cursos = cursosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cursos);
    }

    /**
     * {@code DELETE  /cursos/:id} : delete the "id" cursos.
     *
     * @param id the id of the cursos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCursos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Cursos : {}", id);
        cursosRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
