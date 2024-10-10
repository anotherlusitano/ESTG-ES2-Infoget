package com.es2.infoget.web.rest;

import com.es2.infoget.domain.Secretarios;
import com.es2.infoget.repository.SecretariosRepository;
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
 * REST controller for managing {@link com.es2.infoget.domain.Secretarios}.
 */
@RestController
@RequestMapping("/api/secretarios")
@Transactional
public class SecretariosResource {

    private static final Logger LOG = LoggerFactory.getLogger(SecretariosResource.class);

    private static final String ENTITY_NAME = "secretarios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecretariosRepository secretariosRepository;

    public SecretariosResource(SecretariosRepository secretariosRepository) {
        this.secretariosRepository = secretariosRepository;
    }

    /**
     * {@code POST  /secretarios} : Create a new secretarios.
     *
     * @param secretarios the secretarios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secretarios, or with status {@code 400 (Bad Request)} if the secretarios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Secretarios> createSecretarios(@Valid @RequestBody Secretarios secretarios) throws URISyntaxException {
        LOG.debug("REST request to save Secretarios : {}", secretarios);
        if (secretarios.getId() != null) {
            throw new BadRequestAlertException("A new secretarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        secretarios = secretariosRepository.save(secretarios);
        return ResponseEntity.created(new URI("/api/secretarios/" + secretarios.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, secretarios.getId().toString()))
            .body(secretarios);
    }

    /**
     * {@code PUT  /secretarios/:id} : Updates an existing secretarios.
     *
     * @param id the id of the secretarios to save.
     * @param secretarios the secretarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secretarios,
     * or with status {@code 400 (Bad Request)} if the secretarios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secretarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Secretarios> updateSecretarios(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Secretarios secretarios
    ) throws URISyntaxException {
        LOG.debug("REST request to update Secretarios : {}, {}", id, secretarios);
        if (secretarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secretarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secretariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        secretarios = secretariosRepository.save(secretarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secretarios.getId().toString()))
            .body(secretarios);
    }

    /**
     * {@code PATCH  /secretarios/:id} : Partial updates given fields of an existing secretarios, field will ignore if it is null
     *
     * @param id the id of the secretarios to save.
     * @param secretarios the secretarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secretarios,
     * or with status {@code 400 (Bad Request)} if the secretarios is not valid,
     * or with status {@code 404 (Not Found)} if the secretarios is not found,
     * or with status {@code 500 (Internal Server Error)} if the secretarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Secretarios> partialUpdateSecretarios(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Secretarios secretarios
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Secretarios partially : {}, {}", id, secretarios);
        if (secretarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secretarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secretariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Secretarios> result = secretariosRepository
            .findById(secretarios.getId())
            .map(existingSecretarios -> {
                if (secretarios.getNomesecretario() != null) {
                    existingSecretarios.setNomesecretario(secretarios.getNomesecretario());
                }
                if (secretarios.getEmail() != null) {
                    existingSecretarios.setEmail(secretarios.getEmail());
                }
                if (secretarios.getPassword() != null) {
                    existingSecretarios.setPassword(secretarios.getPassword());
                }

                return existingSecretarios;
            })
            .map(secretariosRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secretarios.getId().toString())
        );
    }

    /**
     * {@code GET  /secretarios} : get all the secretarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secretarios in body.
     */
    @GetMapping("")
    public List<Secretarios> getAllSecretarios() {
        LOG.debug("REST request to get all Secretarios");
        return secretariosRepository.findAll();
    }

    /**
     * {@code GET  /secretarios/:id} : get the "id" secretarios.
     *
     * @param id the id of the secretarios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secretarios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Secretarios> getSecretarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Secretarios : {}", id);
        Optional<Secretarios> secretarios = secretariosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(secretarios);
    }

    /**
     * {@code DELETE  /secretarios/:id} : delete the "id" secretarios.
     *
     * @param id the id of the secretarios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecretarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Secretarios : {}", id);
        secretariosRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
