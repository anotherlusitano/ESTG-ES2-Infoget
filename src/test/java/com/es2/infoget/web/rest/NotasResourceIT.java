package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.NotasAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Notas;
import com.es2.infoget.repository.NotasRepository;
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
 * Integration tests for the {@link NotasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotasResourceIT {

    private static final Integer DEFAULT_NOTA = 0;
    private static final Integer UPDATED_NOTA = 1;

    private static final String ENTITY_API_URL = "/api/notas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotasMockMvc;

    private Notas notas;

    private Notas insertedNotas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notas createEntity() {
        return new Notas().nota(DEFAULT_NOTA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notas createUpdatedEntity() {
        return new Notas().nota(UPDATED_NOTA);
    }

    @BeforeEach
    public void initTest() {
        notas = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNotas != null) {
            notasRepository.delete(insertedNotas);
            insertedNotas = null;
        }
    }

    @Test
    @Transactional
    void createNotas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Notas
        var returnedNotas = om.readValue(
            restNotasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Notas.class
        );

        // Validate the Notas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNotasUpdatableFieldsEquals(returnedNotas, getPersistedNotas(returnedNotas));

        insertedNotas = returnedNotas;
    }

    @Test
    @Transactional
    void createNotasWithExistingId() throws Exception {
        // Create the Notas with an existing ID
        notas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notas)))
            .andExpect(status().isBadRequest());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotas() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        // Get all the notasList
        restNotasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)));
    }

    @Test
    @Transactional
    void getNotas() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        // Get the notas
        restNotasMockMvc
            .perform(get(ENTITY_API_URL_ID, notas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notas.getId().intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA));
    }

    @Test
    @Transactional
    void getNonExistingNotas() throws Exception {
        // Get the notas
        restNotasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotas() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notas
        Notas updatedNotas = notasRepository.findById(notas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNotas are not directly saved in db
        em.detach(updatedNotas);
        updatedNotas.nota(UPDATED_NOTA);

        restNotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNotas))
            )
            .andExpect(status().isOk());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNotasToMatchAllProperties(updatedNotas);
    }

    @Test
    @Transactional
    void putNonExistingNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(put(ENTITY_API_URL_ID, notas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notas)))
            .andExpect(status().isBadRequest());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotasWithPatch() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notas using partial update
        Notas partialUpdatedNotas = new Notas();
        partialUpdatedNotas.setId(notas.getId());

        restNotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotas))
            )
            .andExpect(status().isOk());

        // Validate the Notas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNotas, notas), getPersistedNotas(notas));
    }

    @Test
    @Transactional
    void fullUpdateNotasWithPatch() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notas using partial update
        Notas partialUpdatedNotas = new Notas();
        partialUpdatedNotas.setId(notas.getId());

        partialUpdatedNotas.nota(UPDATED_NOTA);

        restNotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotas))
            )
            .andExpect(status().isOk());

        // Validate the Notas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotasUpdatableFieldsEquals(partialUpdatedNotas, getPersistedNotas(partialUpdatedNotas));
    }

    @Test
    @Transactional
    void patchNonExistingNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notas.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotas() throws Exception {
        // Initialize the database
        insertedNotas = notasRepository.saveAndFlush(notas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the notas
        restNotasMockMvc
            .perform(delete(ENTITY_API_URL_ID, notas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return notasRepository.count();
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

    protected Notas getPersistedNotas(Notas notas) {
        return notasRepository.findById(notas.getId()).orElseThrow();
    }

    protected void assertPersistedNotasToMatchAllProperties(Notas expectedNotas) {
        assertNotasAllPropertiesEquals(expectedNotas, getPersistedNotas(expectedNotas));
    }

    protected void assertPersistedNotasToMatchUpdatableProperties(Notas expectedNotas) {
        assertNotasAllUpdatablePropertiesEquals(expectedNotas, getPersistedNotas(expectedNotas));
    }
}
