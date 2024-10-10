package com.es2.infoget.web.rest;

import static com.es2.infoget.domain.DisciplinasAsserts.*;
import static com.es2.infoget.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.es2.infoget.IntegrationTest;
import com.es2.infoget.domain.Disciplinas;
import com.es2.infoget.repository.DisciplinasRepository;
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
 * Integration tests for the {@link DisciplinasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DisciplinasResourceIT {

    private static final String DEFAULT_NOMEDISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_NOMEDISCIPLINA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CARGAHORARIA = 0;
    private static final Integer UPDATED_CARGAHORARIA = 1;

    private static final String ENTITY_API_URL = "/api/disciplinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DisciplinasRepository disciplinasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplinasMockMvc;

    private Disciplinas disciplinas;

    private Disciplinas insertedDisciplinas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplinas createEntity() {
        return new Disciplinas().nomedisciplina(DEFAULT_NOMEDISCIPLINA).cargahoraria(DEFAULT_CARGAHORARIA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplinas createUpdatedEntity() {
        return new Disciplinas().nomedisciplina(UPDATED_NOMEDISCIPLINA).cargahoraria(UPDATED_CARGAHORARIA);
    }

    @BeforeEach
    public void initTest() {
        disciplinas = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDisciplinas != null) {
            disciplinasRepository.delete(insertedDisciplinas);
            insertedDisciplinas = null;
        }
    }

    @Test
    @Transactional
    void createDisciplinas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Disciplinas
        var returnedDisciplinas = om.readValue(
            restDisciplinasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Disciplinas.class
        );

        // Validate the Disciplinas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDisciplinasUpdatableFieldsEquals(returnedDisciplinas, getPersistedDisciplinas(returnedDisciplinas));

        insertedDisciplinas = returnedDisciplinas;
    }

    @Test
    @Transactional
    void createDisciplinasWithExistingId() throws Exception {
        // Create the Disciplinas with an existing ID
        disciplinas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinas)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomedisciplinaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        disciplinas.setNomedisciplina(null);

        // Create the Disciplinas, which fails.

        restDisciplinasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinas)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDisciplinas() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        // Get all the disciplinasList
        restDisciplinasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplinas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomedisciplina").value(hasItem(DEFAULT_NOMEDISCIPLINA)))
            .andExpect(jsonPath("$.[*].cargahoraria").value(hasItem(DEFAULT_CARGAHORARIA)));
    }

    @Test
    @Transactional
    void getDisciplinas() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        // Get the disciplinas
        restDisciplinasMockMvc
            .perform(get(ENTITY_API_URL_ID, disciplinas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplinas.getId().intValue()))
            .andExpect(jsonPath("$.nomedisciplina").value(DEFAULT_NOMEDISCIPLINA))
            .andExpect(jsonPath("$.cargahoraria").value(DEFAULT_CARGAHORARIA));
    }

    @Test
    @Transactional
    void getNonExistingDisciplinas() throws Exception {
        // Get the disciplinas
        restDisciplinasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDisciplinas() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinas
        Disciplinas updatedDisciplinas = disciplinasRepository.findById(disciplinas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDisciplinas are not directly saved in db
        em.detach(updatedDisciplinas);
        updatedDisciplinas.nomedisciplina(UPDATED_NOMEDISCIPLINA).cargahoraria(UPDATED_CARGAHORARIA);

        restDisciplinasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDisciplinas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDisciplinas))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDisciplinasToMatchAllProperties(updatedDisciplinas);
    }

    @Test
    @Transactional
    void putNonExistingDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplinas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disciplinas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disciplinas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disciplinas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisciplinasWithPatch() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinas using partial update
        Disciplinas partialUpdatedDisciplinas = new Disciplinas();
        partialUpdatedDisciplinas.setId(disciplinas.getId());

        partialUpdatedDisciplinas.nomedisciplina(UPDATED_NOMEDISCIPLINA);

        restDisciplinasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisciplinas))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisciplinasUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDisciplinas, disciplinas),
            getPersistedDisciplinas(disciplinas)
        );
    }

    @Test
    @Transactional
    void fullUpdateDisciplinasWithPatch() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disciplinas using partial update
        Disciplinas partialUpdatedDisciplinas = new Disciplinas();
        partialUpdatedDisciplinas.setId(disciplinas.getId());

        partialUpdatedDisciplinas.nomedisciplina(UPDATED_NOMEDISCIPLINA).cargahoraria(UPDATED_CARGAHORARIA);

        restDisciplinasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisciplinas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisciplinas))
            )
            .andExpect(status().isOk());

        // Validate the Disciplinas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisciplinasUpdatableFieldsEquals(partialUpdatedDisciplinas, getPersistedDisciplinas(partialUpdatedDisciplinas));
    }

    @Test
    @Transactional
    void patchNonExistingDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disciplinas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disciplinas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disciplinas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisciplinas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disciplinas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplinasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(disciplinas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disciplinas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisciplinas() throws Exception {
        // Initialize the database
        insertedDisciplinas = disciplinasRepository.saveAndFlush(disciplinas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the disciplinas
        restDisciplinasMockMvc
            .perform(delete(ENTITY_API_URL_ID, disciplinas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return disciplinasRepository.count();
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

    protected Disciplinas getPersistedDisciplinas(Disciplinas disciplinas) {
        return disciplinasRepository.findById(disciplinas.getId()).orElseThrow();
    }

    protected void assertPersistedDisciplinasToMatchAllProperties(Disciplinas expectedDisciplinas) {
        assertDisciplinasAllPropertiesEquals(expectedDisciplinas, getPersistedDisciplinas(expectedDisciplinas));
    }

    protected void assertPersistedDisciplinasToMatchUpdatableProperties(Disciplinas expectedDisciplinas) {
        assertDisciplinasAllUpdatablePropertiesEquals(expectedDisciplinas, getPersistedDisciplinas(expectedDisciplinas));
    }
}
