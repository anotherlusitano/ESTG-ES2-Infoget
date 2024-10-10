package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.CursosAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Cursos;
import com.es2.infoget.repository.CursosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CursosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CursosResourceIT {

    private static final String DEFAULT_NOMECURSO = "AAAAAAAAAA";
    private static final String UPDATED_NOMECURSO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCursosMockMvc;

    private Cursos cursos;

    private Cursos insertedCursos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cursos createEntity() {
        return new Cursos().nomecurso(DEFAULT_NOMECURSO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cursos createUpdatedEntity() {
        return new Cursos().nomecurso(UPDATED_NOMECURSO);
    }

    @BeforeEach
    public void initTest() {
        cursos = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCursos != null) {
            cursosRepository.delete(insertedCursos);
            insertedCursos = null;
        }
    }

    @Test
    @Transactional
    void createCursos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cursos
        var returnedCursos = om.readValue(
            restCursosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursos)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Cursos.class
        );

        // Validate the Cursos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCursosUpdatableFieldsEquals(returnedCursos, getPersistedCursos(returnedCursos));

        insertedCursos = returnedCursos;
    }

    @Test
    @Transactional
    void createCursosWithExistingId() throws Exception {
        // Create the Cursos with an existing ID
        cursos.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursos)))
            .andExpect(status().isBadRequest());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomecursoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cursos.setNomecurso(null);

        // Create the Cursos, which fails.

        restCursosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCursos() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        // Get all the cursosList
        restCursosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cursos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomecurso").value(hasItem(DEFAULT_NOMECURSO)));
    }

    @Test
    @Transactional
    void getCursos() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        // Get the cursos
        restCursosMockMvc
            .perform(get(ENTITY_API_URL_ID, cursos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cursos.getId().intValue()))
            .andExpect(jsonPath("$.nomecurso").value(DEFAULT_NOMECURSO));
    }

    @Test
    @Transactional
    void getNonExistingCursos() throws Exception {
        // Get the cursos
        restCursosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCursos() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursos
        Cursos updatedCursos = cursosRepository.findById(cursos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCursos are not directly saved in db
        em.detach(updatedCursos);
        updatedCursos.nomecurso(UPDATED_NOMECURSO);

        restCursosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCursos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCursos))
            )
            .andExpect(status().isOk());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCursosToMatchAllProperties(updatedCursos);
    }

    @Test
    @Transactional
    void putNonExistingCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(put(ENTITY_API_URL_ID, cursos.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursos)))
            .andExpect(status().isBadRequest());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cursos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCursosWithPatch() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursos using partial update
        Cursos partialUpdatedCursos = new Cursos();
        partialUpdatedCursos.setId(cursos.getId());

        restCursosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCursos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCursos))
            )
            .andExpect(status().isOk());

        // Validate the Cursos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCursosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCursos, cursos), getPersistedCursos(cursos));
    }

    @Test
    @Transactional
    void fullUpdateCursosWithPatch() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursos using partial update
        Cursos partialUpdatedCursos = new Cursos();
        partialUpdatedCursos.setId(cursos.getId());

        partialUpdatedCursos.nomecurso(UPDATED_NOMECURSO);

        restCursosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCursos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCursos))
            )
            .andExpect(status().isOk());

        // Validate the Cursos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCursosUpdatableFieldsEquals(partialUpdatedCursos, getPersistedCursos(partialUpdatedCursos));
    }

    @Test
    @Transactional
    void patchNonExistingCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cursos.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cursos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cursos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCursos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cursos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cursos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCursos() throws Exception {
        // Initialize the database
        insertedCursos = cursosRepository.saveAndFlush(cursos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cursos
        restCursosMockMvc
            .perform(delete(ENTITY_API_URL_ID, cursos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cursosRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Cursos getPersistedCursos(Cursos cursos) {
        return cursosRepository.findById(cursos.getId()).orElseThrow();
    }

    protected void assertPersistedCursosToMatchAllProperties(Cursos expectedCursos) {
        assertCursosAllPropertiesEquals(expectedCursos, getPersistedCursos(expectedCursos));
    }

    protected void assertPersistedCursosToMatchUpdatableProperties(Cursos expectedCursos) {
        assertCursosAllUpdatablePropertiesEquals(expectedCursos, getPersistedCursos(expectedCursos));
    }
}
