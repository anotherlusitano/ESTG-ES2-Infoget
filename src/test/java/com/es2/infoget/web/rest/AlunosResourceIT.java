package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.AlunosAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Alunos;
import com.es2.infoget.repository.AlunosRepository;
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
 * Integration tests for the {@link AlunosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlunosResourceIT {

    private static final String DEFAULT_NOMEALUNO = "AAAAAAAAAA";
    private static final String UPDATED_NOMEALUNO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "tf@ksj.cgmdyyg";
    private static final String UPDATED_EMAIL = "1mhdw7@2bf.ktytcc";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alunos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AlunosRepository alunosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlunosMockMvc;

    private Alunos alunos;

    private Alunos insertedAlunos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alunos createEntity() {
        return new Alunos().nomealuno(DEFAULT_NOMEALUNO).email(DEFAULT_EMAIL).password(DEFAULT_PASSWORD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alunos createUpdatedEntity() {
        return new Alunos().nomealuno(UPDATED_NOMEALUNO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);
    }

    @BeforeEach
    public void initTest() {
        alunos = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAlunos != null) {
            alunosRepository.delete(insertedAlunos);
            insertedAlunos = null;
        }
    }

    @Test
    @Transactional
    void createAlunos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Alunos
        var returnedAlunos = om.readValue(
            restAlunosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Alunos.class
        );

        // Validate the Alunos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAlunosUpdatableFieldsEquals(returnedAlunos, getPersistedAlunos(returnedAlunos));

        insertedAlunos = returnedAlunos;
    }

    @Test
    @Transactional
    void createAlunosWithExistingId() throws Exception {
        // Create the Alunos with an existing ID
        alunos.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isBadRequest());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomealunoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alunos.setNomealuno(null);

        // Create the Alunos, which fails.

        restAlunosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alunos.setEmail(null);

        // Create the Alunos, which fails.

        restAlunosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alunos.setPassword(null);

        // Create the Alunos, which fails.

        restAlunosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlunos() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        // Get all the alunosList
        restAlunosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alunos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomealuno").value(hasItem(DEFAULT_NOMEALUNO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getAlunos() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        // Get the alunos
        restAlunosMockMvc
            .perform(get(ENTITY_API_URL_ID, alunos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alunos.getId().intValue()))
            .andExpect(jsonPath("$.nomealuno").value(DEFAULT_NOMEALUNO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingAlunos() throws Exception {
        // Get the alunos
        restAlunosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlunos() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alunos
        Alunos updatedAlunos = alunosRepository.findById(alunos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAlunos are not directly saved in db
        em.detach(updatedAlunos);
        updatedAlunos.nomealuno(UPDATED_NOMEALUNO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restAlunosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlunos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAlunos))
            )
            .andExpect(status().isOk());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAlunosToMatchAllProperties(updatedAlunos);
    }

    @Test
    @Transactional
    void putNonExistingAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(put(ENTITY_API_URL_ID, alunos.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isBadRequest());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alunos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlunosWithPatch() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alunos using partial update
        Alunos partialUpdatedAlunos = new Alunos();
        partialUpdatedAlunos.setId(alunos.getId());

        partialUpdatedAlunos.nomealuno(UPDATED_NOMEALUNO).password(UPDATED_PASSWORD);

        restAlunosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlunos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlunos))
            )
            .andExpect(status().isOk());

        // Validate the Alunos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlunosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAlunos, alunos), getPersistedAlunos(alunos));
    }

    @Test
    @Transactional
    void fullUpdateAlunosWithPatch() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alunos using partial update
        Alunos partialUpdatedAlunos = new Alunos();
        partialUpdatedAlunos.setId(alunos.getId());

        partialUpdatedAlunos.nomealuno(UPDATED_NOMEALUNO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restAlunosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlunos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlunos))
            )
            .andExpect(status().isOk());

        // Validate the Alunos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlunosUpdatableFieldsEquals(partialUpdatedAlunos, getPersistedAlunos(partialUpdatedAlunos));
    }

    @Test
    @Transactional
    void patchNonExistingAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alunos.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(alunos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alunos))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlunos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alunos.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(alunos)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alunos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlunos() throws Exception {
        // Initialize the database
        insertedAlunos = alunosRepository.saveAndFlush(alunos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the alunos
        restAlunosMockMvc
            .perform(delete(ENTITY_API_URL_ID, alunos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return alunosRepository.count();
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

    protected Alunos getPersistedAlunos(Alunos alunos) {
        return alunosRepository.findById(alunos.getId()).orElseThrow();
    }

    protected void assertPersistedAlunosToMatchAllProperties(Alunos expectedAlunos) {
        assertAlunosAllPropertiesEquals(expectedAlunos, getPersistedAlunos(expectedAlunos));
    }

    protected void assertPersistedAlunosToMatchUpdatableProperties(Alunos expectedAlunos) {
        assertAlunosAllUpdatablePropertiesEquals(expectedAlunos, getPersistedAlunos(expectedAlunos));
    }
}
