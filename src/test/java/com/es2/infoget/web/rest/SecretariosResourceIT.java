package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.SecretariosAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Secretarios;
import com.es2.infoget.repository.SecretariosRepository;
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
 * Integration tests for the {@link SecretariosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecretariosResourceIT {

    private static final String DEFAULT_NOMESECRETARIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMESECRETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "or_i@iad.hlgpke";
    private static final String UPDATED_EMAIL = "1k2u8w@pey.yv";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/secretarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SecretariosRepository secretariosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecretariosMockMvc;

    private Secretarios secretarios;

    private Secretarios insertedSecretarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Secretarios createEntity() {
        return new Secretarios().nomesecretario(DEFAULT_NOMESECRETARIO).email(DEFAULT_EMAIL).password(DEFAULT_PASSWORD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Secretarios createUpdatedEntity() {
        return new Secretarios().nomesecretario(UPDATED_NOMESECRETARIO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);
    }

    @BeforeEach
    public void initTest() {
        secretarios = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSecretarios != null) {
            secretariosRepository.delete(insertedSecretarios);
            insertedSecretarios = null;
        }
    }

    @Test
    @Transactional
    void createSecretarios() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Secretarios
        var returnedSecretarios = om.readValue(
            restSecretariosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Secretarios.class
        );

        // Validate the Secretarios in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSecretariosUpdatableFieldsEquals(returnedSecretarios, getPersistedSecretarios(returnedSecretarios));

        insertedSecretarios = returnedSecretarios;
    }

    @Test
    @Transactional
    void createSecretariosWithExistingId() throws Exception {
        // Create the Secretarios with an existing ID
        secretarios.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecretariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isBadRequest());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomesecretarioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        secretarios.setNomesecretario(null);

        // Create the Secretarios, which fails.

        restSecretariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        secretarios.setEmail(null);

        // Create the Secretarios, which fails.

        restSecretariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        secretarios.setPassword(null);

        // Create the Secretarios, which fails.

        restSecretariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSecretarios() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        // Get all the secretariosList
        restSecretariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secretarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomesecretario").value(hasItem(DEFAULT_NOMESECRETARIO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getSecretarios() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        // Get the secretarios
        restSecretariosMockMvc
            .perform(get(ENTITY_API_URL_ID, secretarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secretarios.getId().intValue()))
            .andExpect(jsonPath("$.nomesecretario").value(DEFAULT_NOMESECRETARIO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingSecretarios() throws Exception {
        // Get the secretarios
        restSecretariosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSecretarios() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secretarios
        Secretarios updatedSecretarios = secretariosRepository.findById(secretarios.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSecretarios are not directly saved in db
        em.detach(updatedSecretarios);
        updatedSecretarios.nomesecretario(UPDATED_NOMESECRETARIO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restSecretariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecretarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSecretarios))
            )
            .andExpect(status().isOk());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSecretariosToMatchAllProperties(updatedSecretarios);
    }

    @Test
    @Transactional
    void putNonExistingSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, secretarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(secretarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(secretarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecretariosWithPatch() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secretarios using partial update
        Secretarios partialUpdatedSecretarios = new Secretarios();
        partialUpdatedSecretarios.setId(secretarios.getId());

        partialUpdatedSecretarios.email(UPDATED_EMAIL);

        restSecretariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecretarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSecretarios))
            )
            .andExpect(status().isOk());

        // Validate the Secretarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSecretariosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSecretarios, secretarios),
            getPersistedSecretarios(secretarios)
        );
    }

    @Test
    @Transactional
    void fullUpdateSecretariosWithPatch() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secretarios using partial update
        Secretarios partialUpdatedSecretarios = new Secretarios();
        partialUpdatedSecretarios.setId(secretarios.getId());

        partialUpdatedSecretarios.nomesecretario(UPDATED_NOMESECRETARIO).email(UPDATED_EMAIL).password(UPDATED_PASSWORD);

        restSecretariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecretarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSecretarios))
            )
            .andExpect(status().isOk());

        // Validate the Secretarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSecretariosUpdatableFieldsEquals(partialUpdatedSecretarios, getPersistedSecretarios(partialUpdatedSecretarios));
    }

    @Test
    @Transactional
    void patchNonExistingSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, secretarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(secretarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(secretarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecretarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secretarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecretariosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(secretarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Secretarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecretarios() throws Exception {
        // Initialize the database
        insertedSecretarios = secretariosRepository.saveAndFlush(secretarios);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the secretarios
        restSecretariosMockMvc
            .perform(delete(ENTITY_API_URL_ID, secretarios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return secretariosRepository.count();
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

    protected Secretarios getPersistedSecretarios(Secretarios secretarios) {
        return secretariosRepository.findById(secretarios.getId()).orElseThrow();
    }

    protected void assertPersistedSecretariosToMatchAllProperties(Secretarios expectedSecretarios) {
        assertSecretariosAllPropertiesEquals(expectedSecretarios, getPersistedSecretarios(expectedSecretarios));
    }

    protected void assertPersistedSecretariosToMatchUpdatableProperties(Secretarios expectedSecretarios) {
        assertSecretariosAllUpdatablePropertiesEquals(expectedSecretarios, getPersistedSecretarios(expectedSecretarios));
    }
}
