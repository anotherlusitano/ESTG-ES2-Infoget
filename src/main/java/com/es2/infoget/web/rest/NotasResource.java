package com.es2.infoget.web.rest;

import com.es2.infoget.domain.Notas;
import com.es2.infoget.repository.NotasRepository;
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
 * REST controller for managing {@link com.es2.infoget.domain.Notas}.
 */
@RestController
@RequestMapping("/api/notas")
@Transactional
public class NotasResource {

    private static final Logger LOG = LoggerFactory.getLogger(NotasResource.class);

    private static final String ENTITY_NAME = "notas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotasRepository notasRepository;

    public NotasResource(NotasRepository notasRepository) {
        this.notasRepository = notasRepository;
    }

    /**
     * {@code POST  /notas} : Create a new notas.
     *
     * @param notas the notas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notas, or with status {@code 400 (Bad Request)} if the notas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Notas> createNotas(@Valid @RequestBody Notas notas) throws URISyntaxException {
        LOG.debug("REST request to save Notas : {}", notas);
        if (notas.getId() != null) {
            throw new BadRequestAlertException("A new notas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        notas = notasRepository.save(notas);
        return ResponseEntity.created(new URI("/api/notas/" + notas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, notas.getId().toString()))
            .body(notas);
    }

    /**
     * {@code PUT  /notas/:id} : Updates an existing notas.
     *
     * @param id the id of the notas to save.
     * @param notas the notas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notas,
     * or with status {@code 400 (Bad Request)} if the notas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notas> updateNotas(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Notas notas)
        throws URISyntaxException {
        LOG.debug("REST request to update Notas : {}, {}", id, notas);
        if (notas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        notas = notasRepository.save(notas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notas.getId().toString()))
            .body(notas);
    }

    /**
     * {@code PATCH  /notas/:id} : Partial updates given fields of an existing notas, field will ignore if it is null
     *
     * @param id the id of the notas to save.
     * @param notas the notas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notas,
     * or with status {@code 400 (Bad Request)} if the notas is not valid,
     * or with status {@code 404 (Not Found)} if the notas is not found,
     * or with status {@code 500 (Internal Server Error)} if the notas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Notas> partialUpdateNotas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Notas notas
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Notas partially : {}, {}", id, notas);
        if (notas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Notas> result = notasRepository
            .findById(notas.getId())
            .map(existingNotas -> {
                if (notas.getNota() != null) {
                    existingNotas.setNota(notas.getNota());
                }

                return existingNotas;
            })
            .map(notasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notas.getId().toString())
        );
    }

    /**
     * {@code GET  /notas} : get all the notas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notas in body.
     */
    @GetMapping("")
    public List<Notas> getAllNotas() {
        LOG.debug("REST request to get all Notas");
        return notasRepository.findAll();
    }

    /**
     * {@code GET  /notas/:id} : get the "id" notas.
     *
     * @param id the id of the notas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notas> getNotas(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Notas : {}", id);
        Optional<Notas> notas = notasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notas);
    }

    /**
     * {@code DELETE  /notas/:id} : delete the "id" notas.
     *
     * @param id the id of the notas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotas(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Notas : {}", id);
        notasRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
