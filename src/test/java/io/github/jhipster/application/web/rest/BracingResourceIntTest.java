package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.ApdSampleApplicationApp;

import io.github.jhipster.application.domain.Bracing;
import io.github.jhipster.application.repository.BracingRepository;
import io.github.jhipster.application.service.BracingService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BracingResource REST controller.
 *
 * @see BracingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApdSampleApplicationApp.class)
public class BracingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BracingRepository bracingRepository;

    @Autowired
    private BracingService bracingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBracingMockMvc;

    private Bracing bracing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BracingResource bracingResource = new BracingResource(bracingService);
        this.restBracingMockMvc = MockMvcBuilders.standaloneSetup(bracingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bracing createEntity(EntityManager em) {
        Bracing bracing = new Bracing()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return bracing;
    }

    @Before
    public void initTest() {
        bracing = createEntity(em);
    }

    @Test
    @Transactional
    public void createBracing() throws Exception {
        int databaseSizeBeforeCreate = bracingRepository.findAll().size();

        // Create the Bracing
        restBracingMockMvc.perform(post("/api/bracings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bracing)))
            .andExpect(status().isCreated());

        // Validate the Bracing in the database
        List<Bracing> bracingList = bracingRepository.findAll();
        assertThat(bracingList).hasSize(databaseSizeBeforeCreate + 1);
        Bracing testBracing = bracingList.get(bracingList.size() - 1);
        assertThat(testBracing.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBracing.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBracingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bracingRepository.findAll().size();

        // Create the Bracing with an existing ID
        bracing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBracingMockMvc.perform(post("/api/bracings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bracing)))
            .andExpect(status().isBadRequest());

        // Validate the Bracing in the database
        List<Bracing> bracingList = bracingRepository.findAll();
        assertThat(bracingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBracings() throws Exception {
        // Initialize the database
        bracingRepository.saveAndFlush(bracing);

        // Get all the bracingList
        restBracingMockMvc.perform(get("/api/bracings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bracing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getBracing() throws Exception {
        // Initialize the database
        bracingRepository.saveAndFlush(bracing);

        // Get the bracing
        restBracingMockMvc.perform(get("/api/bracings/{id}", bracing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bracing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBracing() throws Exception {
        // Get the bracing
        restBracingMockMvc.perform(get("/api/bracings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBracing() throws Exception {
        // Initialize the database
        bracingService.save(bracing);

        int databaseSizeBeforeUpdate = bracingRepository.findAll().size();

        // Update the bracing
        Bracing updatedBracing = bracingRepository.findOne(bracing.getId());
        // Disconnect from session so that the updates on updatedBracing are not directly saved in db
        em.detach(updatedBracing);
        updatedBracing
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restBracingMockMvc.perform(put("/api/bracings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBracing)))
            .andExpect(status().isOk());

        // Validate the Bracing in the database
        List<Bracing> bracingList = bracingRepository.findAll();
        assertThat(bracingList).hasSize(databaseSizeBeforeUpdate);
        Bracing testBracing = bracingList.get(bracingList.size() - 1);
        assertThat(testBracing.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBracing.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBracing() throws Exception {
        int databaseSizeBeforeUpdate = bracingRepository.findAll().size();

        // Create the Bracing

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBracingMockMvc.perform(put("/api/bracings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bracing)))
            .andExpect(status().isCreated());

        // Validate the Bracing in the database
        List<Bracing> bracingList = bracingRepository.findAll();
        assertThat(bracingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBracing() throws Exception {
        // Initialize the database
        bracingService.save(bracing);

        int databaseSizeBeforeDelete = bracingRepository.findAll().size();

        // Get the bracing
        restBracingMockMvc.perform(delete("/api/bracings/{id}", bracing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bracing> bracingList = bracingRepository.findAll();
        assertThat(bracingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bracing.class);
        Bracing bracing1 = new Bracing();
        bracing1.setId(1L);
        Bracing bracing2 = new Bracing();
        bracing2.setId(bracing1.getId());
        assertThat(bracing1).isEqualTo(bracing2);
        bracing2.setId(2L);
        assertThat(bracing1).isNotEqualTo(bracing2);
        bracing1.setId(null);
        assertThat(bracing1).isNotEqualTo(bracing2);
    }
}
