package com.es2.infoget.web.rest;

import com.es2.infoget.domain.CursoDisciplina;
import com.es2.infoget.repository.CursoDisciplinaRepository;
import com.es2.infoget.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.es2.infoget.domain.CursoDisciplina}.
 */
@RestController
@RequestMapping("/api/curso-disciplinas")
@Transactional
public class CursoDisciplinaResource {

    private static final Logger LOG = LoggerFactory.getLogger(CursoDisciplinaResource.class);

    private static final String ENTITY_NAME = "cursoDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CursoDisciplinaRepository cursoDisciplinaRepository;

    public CursoDisciplinaResource(CursoDisciplinaRepository cursoDisciplinaRepository) {
        this.cursoDisciplinaRepository = cursoDisciplinaRepository;
    }

    /**
     * {@code POST  /curso-disciplinas} : Create a new cursoDisciplina.
     *
     * @param cursoDisciplina the cursoDisciplina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cursoDisciplina, or with status {@code 400 (Bad Request)} if the cursoDisciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CursoDisciplina> createCursoDisciplina(@RequestBody CursoDisciplina cursoDisciplina) throws URISyntaxException {
        LOG.debug("REST request to save CursoDisciplina : {}", cursoDisciplina);
        if (cursoDisciplina.getId() != null) {
            throw new BadRequestAlertException("A new cursoDisciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cursoDisciplina = cursoDisciplinaRepository.save(cursoDisciplina);
        return ResponseEntity.created(new URI("/api/curso-disciplinas/" + cursoDisciplina.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cursoDisciplina.getId().toString()))
            .body(cursoDisciplina);
    }

    /**
     * {@code PUT  /curso-disciplinas/:id} : Updates an existing cursoDisciplina.
     *
     * @param id the id of the cursoDisciplina to save.
     * @param cursoDisciplina the cursoDisciplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursoDisciplina,
     * or with status {@code 400 (Bad Request)} if the cursoDisciplina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cursoDisciplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CursoDisciplina> updateCursoDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CursoDisciplina cursoDisciplina
    ) throws URISyntaxException {
        LOG.debug("REST request to update CursoDisciplina : {}, {}", id, cursoDisciplina);
        if (cursoDisciplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursoDisciplina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cursoDisciplina = cursoDisciplinaRepository.save(cursoDisciplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cursoDisciplina.getId().toString()))
            .body(cursoDisciplina);
    }

    /**
     * {@code PATCH  /curso-disciplinas/:id} : Partial updates given fields of an existing cursoDisciplina, field will ignore if it is null
     *
     * @param id the id of the cursoDisciplina to save.
     * @param cursoDisciplina the cursoDisciplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursoDisciplina,
     * or with status {@code 400 (Bad Request)} if the cursoDisciplina is not valid,
     * or with status {@code 404 (Not Found)} if the cursoDisciplina is not found,
     * or with status {@code 500 (Internal Server Error)} if the cursoDisciplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CursoDisciplina> partialUpdateCursoDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CursoDisciplina cursoDisciplina
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CursoDisciplina partially : {}, {}", id, cursoDisciplina);
        if (cursoDisciplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursoDisciplina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CursoDisciplina> result = cursoDisciplinaRepository.findById(cursoDisciplina.getId()).map(cursoDisciplinaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cursoDisciplina.getId().toString())
        );
    }

    /**
     * {@code GET  /curso-disciplinas} : get all the cursoDisciplinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cursoDisciplinas in body.
     */
    @GetMapping("")
    public List<CursoDisciplina> getAllCursoDisciplinas() {
        LOG.debug("REST request to get all CursoDisciplinas");
        return cursoDisciplinaRepository.findAll();
    }

    /**
     * {@code GET  /curso-disciplinas/:id} : get the "id" cursoDisciplina.
     *
     * @param id the id of the cursoDisciplina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cursoDisciplina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoDisciplina> getCursoDisciplina(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CursoDisciplina : {}", id);
        Optional<CursoDisciplina> cursoDisciplina = cursoDisciplinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cursoDisciplina);
    }

    /**
     * {@code DELETE  /curso-disciplinas/:id} : delete the "id" cursoDisciplina.
     *
     * @param id the id of the cursoDisciplina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCursoDisciplina(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CursoDisciplina : {}", id);
        cursoDisciplinaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
