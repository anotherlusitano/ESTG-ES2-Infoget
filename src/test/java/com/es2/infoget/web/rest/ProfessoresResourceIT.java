package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.ProfessoresAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Professores;
import com.es2.infoget.repository.ProfessoresRepository;
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
 * Integration tests for the {@link ProfessoresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfessoresResourceIT {

    private static final String DEFAULT_NOMEPROFESSOR = "AAAAAAAAAA";
    private static final String UPDATED_NOMEPROFESSOR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "%1g+9@6e.mpnsuq";
    private static final String UPDATED_EMAIL = "t99leh@dkqf.wphhbjc";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/professores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfessoresRepository professoresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfessoresMockMvc;

    private Professores professores;

    private Professores insertedProfessores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professores createEntity() {
        return new Professores().nomeprofessor(DEFAULT_NOMEPROFESSOR).email(DEFAULT_EMAIL).password(DEFAULT_PASSWORD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professores createUpdatedEntity() {
        return new Professores().nomeprofessor(UPDATED_NOMEPROFESSOR).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);
    }

    @BeforeEach
    public void initTest() {
        professores = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProfessores != null) {
            professoresRepository.delete(insertedProfessores);
            insertedProfessores = null;
        }
    }

    @Test
    @Transactional
    void createProfessores() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Professores
        var returnedProfessores = om.readValue(
            restProfessoresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Professores.class
        );

        // Validate the Professores in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProfessoresUpdatableFieldsEquals(returnedProfessores, getPersistedProfessores(returnedProfessores));

        insertedProfessores = returnedProfessores;
    }

    @Test
    @Transactional
    void createProfessoresWithExistingId() throws Exception {
        // Create the Professores with an existing ID
        professores.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
            .andExpect(status().isBadRequest());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeprofessorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        professores.setNomeprofessor(null);

        // Create the Professores, which fails.

        restProfessoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        professores.setEmail(null);

        // Create the Professores, which fails.

        restProfessoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        professores.setPassword(null);

        // Create the Professores, which fails.

        restProfessoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfessores() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        // Get all the professoresList
        restProfessoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeprofessor").value(hasItem(DEFAULT_NOMEPROFESSOR)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getProfessores() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        // Get the professores
        restProfessoresMockMvc
            .perform(get(ENTITY_API_URL_ID, professores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professores.getId().intValue()))
            .andExpect(jsonPath("$.nomeprofessor").value(DEFAULT_NOMEPROFESSOR))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingProfessores() throws Exception {
        // Get the professores
        restProfessoresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfessores() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professores
        Professores updatedProfessores = professoresRepository.findById(professores.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfessores are not directly saved in db
        em.detach(updatedProfessores);
        updatedProfessores.nomeprofessor(UPDATED_NOMEPROFESSOR).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restProfessoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfessores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProfessores))
            )
            .andExpect(status().isOk());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfessoresToMatchAllProperties(updatedProfessores);
    }

    @Test
    @Transactional
    void putNonExistingProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, professores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(professores))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(professores))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professores)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfessoresWithPatch() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professores using partial update
        Professores partialUpdatedProfessores = new Professores();
        partialUpdatedProfessores.setId(professores.getId());

        partialUpdatedProfessores.nomeprofessor(UPDATED_NOMEPROFESSOR);

        restProfessoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfessores))
            )
            .andExpect(status().isOk());

        // Validate the Professores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfessoresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProfessores, professores),
            getPersistedProfessores(professores)
        );
    }

    @Test
    @Transactional
    void fullUpdateProfessoresWithPatch() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professores using partial update
        Professores partialUpdatedProfessores = new Professores();
        partialUpdatedProfessores.setId(professores.getId());

        partialUpdatedProfessores.nomeprofessor(UPDATED_NOMEPROFESSOR).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restProfessoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfessores))
            )
            .andExpect(status().isOk());

        // Validate the Professores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfessoresUpdatableFieldsEquals(partialUpdatedProfessores, getPersistedProfessores(partialUpdatedProfessores));
    }

    @Test
    @Transactional
    void patchNonExistingProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, professores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(professores))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(professores))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfessores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessoresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(professores)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfessores() throws Exception {
        // Initialize the database
        insertedProfessores = professoresRepository.saveAndFlush(professores);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the professores
        restProfessoresMockMvc
            .perform(delete(ENTITY_API_URL_ID, professores.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return professoresRepository.count();
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

    protected Professores getPersistedProfessores(Professores professores) {
        return professoresRepository.findById(professores.getId()).orElseThrow();
    }

    protected void assertPersistedProfessoresToMatchAllProperties(Professores expectedProfessores) {
        assertProfessoresAllPropertiesEquals(expectedProfessores, getPersistedProfessores(expectedProfessores));
    }

    protected void assertPersistedProfessoresToMatchUpdatableProperties(Professores expectedProfessores) {
        assertProfessoresAllUpdatablePropertiesEquals(expectedProfessores, getPersistedProfessores(expectedProfessores));
    }
}
