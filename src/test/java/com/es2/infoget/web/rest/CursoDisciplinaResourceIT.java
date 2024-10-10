package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.CursoDisciplinaAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.CursoDisciplina;
import com.es2.infoget.repository.CursoDisciplinaRepository;
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
 * Integration tests for the {@link CursoDisciplinaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CursoDisciplinaResourceIT {

    private static final String ENTITY_API_URL = "/api/curso-disciplinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CursoDisciplinaRepository cursoDisciplinaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCursoDisciplinaMockMvc;

    private CursoDisciplina cursoDisciplina;

    private CursoDisciplina insertedCursoDisciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CursoDisciplina createEntity() {
        return new CursoDisciplina();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CursoDisciplina createUpdatedEntity() {
        return new CursoDisciplina();
    }

    @BeforeEach
    public void initTest() {
        cursoDisciplina = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCursoDisciplina != null) {
            cursoDisciplinaRepository.delete(insertedCursoDisciplina);
            insertedCursoDisciplina = null;
        }
    }

    @Test
    @Transactional
    void createCursoDisciplina() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CursoDisciplina
        var returnedCursoDisciplina = om.readValue(
            restCursoDisciplinaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursoDisciplina)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CursoDisciplina.class
        );

        // Validate the CursoDisciplina in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCursoDisciplinaUpdatableFieldsEquals(returnedCursoDisciplina, getPersistedCursoDisciplina(returnedCursoDisciplina));

        insertedCursoDisciplina = returnedCursoDisciplina;
    }

    @Test
    @Transactional
    void createCursoDisciplinaWithExistingId() throws Exception {
        // Create the CursoDisciplina with an existing ID
        cursoDisciplina.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursoDisciplinaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursoDisciplina)))
            .andExpect(status().isBadRequest());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCursoDisciplinas() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        // Get all the cursoDisciplinaList
        restCursoDisciplinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cursoDisciplina.getId().intValue())));
    }

    @Test
    @Transactional
    void getCursoDisciplina() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        // Get the cursoDisciplina
        restCursoDisciplinaMockMvc
            .perform(get(ENTITY_API_URL_ID, cursoDisciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cursoDisciplina.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCursoDisciplina() throws Exception {
        // Get the cursoDisciplina
        restCursoDisciplinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCursoDisciplina() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursoDisciplina
        CursoDisciplina updatedCursoDisciplina = cursoDisciplinaRepository.findById(cursoDisciplina.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCursoDisciplina are not directly saved in db
        em.detach(updatedCursoDisciplina);

        restCursoDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCursoDisciplina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCursoDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCursoDisciplinaToMatchAllProperties(updatedCursoDisciplina);
    }

    @Test
    @Transactional
    void putNonExistingCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cursoDisciplina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cursoDisciplina))
            )
            .andExpect(status().isBadRequest());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cursoDisciplina))
            )
            .andExpect(status().isBadRequest());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cursoDisciplina)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCursoDisciplinaWithPatch() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursoDisciplina using partial update
        CursoDisciplina partialUpdatedCursoDisciplina = new CursoDisciplina();
        partialUpdatedCursoDisciplina.setId(cursoDisciplina.getId());

        restCursoDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCursoDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCursoDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the CursoDisciplina in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCursoDisciplinaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCursoDisciplina, cursoDisciplina),
            getPersistedCursoDisciplina(cursoDisciplina)
        );
    }

    @Test
    @Transactional
    void fullUpdateCursoDisciplinaWithPatch() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cursoDisciplina using partial update
        CursoDisciplina partialUpdatedCursoDisciplina = new CursoDisciplina();
        partialUpdatedCursoDisciplina.setId(cursoDisciplina.getId());

        restCursoDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCursoDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCursoDisciplina))
            )
            .andExpect(status().isOk());

        // Validate the CursoDisciplina in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCursoDisciplinaUpdatableFieldsEquals(
            partialUpdatedCursoDisciplina,
            getPersistedCursoDisciplina(partialUpdatedCursoDisciplina)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cursoDisciplina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cursoDisciplina))
            )
            .andExpect(status().isBadRequest());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cursoDisciplina))
            )
            .andExpect(status().isBadRequest());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCursoDisciplina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cursoDisciplina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCursoDisciplinaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cursoDisciplina)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CursoDisciplina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCursoDisciplina() throws Exception {
        // Initialize the database
        insertedCursoDisciplina = cursoDisciplinaRepository.saveAndFlush(cursoDisciplina);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cursoDisciplina
        restCursoDisciplinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cursoDisciplina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cursoDisciplinaRepository.count();
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

    protected CursoDisciplina getPersistedCursoDisciplina(CursoDisciplina cursoDisciplina) {
        return cursoDisciplinaRepository.findById(cursoDisciplina.getId()).orElseThrow();
    }

    protected void assertPersistedCursoDisciplinaToMatchAllProperties(CursoDisciplina expectedCursoDisciplina) {
        assertCursoDisciplinaAllPropertiesEquals(expectedCursoDisciplina, getPersistedCursoDisciplina(expectedCursoDisciplina));
    }

    protected void assertPersistedCursoDisciplinaToMatchUpdatableProperties(CursoDisciplina expectedCursoDisciplina) {
        assertCursoDisciplinaAllUpdatablePropertiesEquals(expectedCursoDisciplina, getPersistedCursoDisciplina(expectedCursoDisciplina));
    }
}
