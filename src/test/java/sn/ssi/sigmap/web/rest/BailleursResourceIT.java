package sn.ssi.sigmap.web.rest;

import sn.ssi.sigmap.BailleursApp;
import sn.ssi.sigmap.config.TestSecurityConfiguration;
import sn.ssi.sigmap.domain.Bailleurs;
import sn.ssi.sigmap.repository.BailleursRepository;
import sn.ssi.sigmap.service.BailleursService;
import sn.ssi.sigmap.service.dto.BailleursDTO;
import sn.ssi.sigmap.service.mapper.BailleursMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BailleursResource} REST controller.
 */
@SpringBootTest(classes = { BailleursApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BailleursResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private BailleursRepository bailleursRepository;

    @Autowired
    private BailleursMapper bailleursMapper;

    @Autowired
    private BailleursService bailleursService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBailleursMockMvc;

    private Bailleurs bailleurs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleurs createEntity(EntityManager em) {
        Bailleurs bailleurs = new Bailleurs()
            .libelle(DEFAULT_LIBELLE);
        return bailleurs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleurs createUpdatedEntity(EntityManager em) {
        Bailleurs bailleurs = new Bailleurs()
            .libelle(UPDATED_LIBELLE);
        return bailleurs;
    }

    @BeforeEach
    public void initTest() {
        bailleurs = createEntity(em);
    }

    @Test
    @Transactional
    public void createBailleurs() throws Exception {
        int databaseSizeBeforeCreate = bailleursRepository.findAll().size();
        // Create the Bailleurs
        BailleursDTO bailleursDTO = bailleursMapper.toDto(bailleurs);
        restBailleursMockMvc.perform(post("/api/bailleurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailleursDTO)))
            .andExpect(status().isCreated());

        // Validate the Bailleurs in the database
        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeCreate + 1);
        Bailleurs testBailleurs = bailleursList.get(bailleursList.size() - 1);
        assertThat(testBailleurs.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createBailleursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bailleursRepository.findAll().size();

        // Create the Bailleurs with an existing ID
        bailleurs.setId(1L);
        BailleursDTO bailleursDTO = bailleursMapper.toDto(bailleurs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBailleursMockMvc.perform(post("/api/bailleurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailleursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bailleurs in the database
        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bailleursRepository.findAll().size();
        // set the field null
        bailleurs.setLibelle(null);

        // Create the Bailleurs, which fails.
        BailleursDTO bailleursDTO = bailleursMapper.toDto(bailleurs);


        restBailleursMockMvc.perform(post("/api/bailleurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailleursDTO)))
            .andExpect(status().isBadRequest());

        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBailleurs() throws Exception {
        // Initialize the database
        bailleursRepository.saveAndFlush(bailleurs);

        // Get all the bailleursList
        restBailleursMockMvc.perform(get("/api/bailleurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bailleurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getBailleurs() throws Exception {
        // Initialize the database
        bailleursRepository.saveAndFlush(bailleurs);

        // Get the bailleurs
        restBailleursMockMvc.perform(get("/api/bailleurs/{id}", bailleurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bailleurs.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingBailleurs() throws Exception {
        // Get the bailleurs
        restBailleursMockMvc.perform(get("/api/bailleurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBailleurs() throws Exception {
        // Initialize the database
        bailleursRepository.saveAndFlush(bailleurs);

        int databaseSizeBeforeUpdate = bailleursRepository.findAll().size();

        // Update the bailleurs
        Bailleurs updatedBailleurs = bailleursRepository.findById(bailleurs.getId()).get();
        // Disconnect from session so that the updates on updatedBailleurs are not directly saved in db
        em.detach(updatedBailleurs);
        updatedBailleurs
            .libelle(UPDATED_LIBELLE);
        BailleursDTO bailleursDTO = bailleursMapper.toDto(updatedBailleurs);

        restBailleursMockMvc.perform(put("/api/bailleurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailleursDTO)))
            .andExpect(status().isOk());

        // Validate the Bailleurs in the database
        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeUpdate);
        Bailleurs testBailleurs = bailleursList.get(bailleursList.size() - 1);
        assertThat(testBailleurs.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingBailleurs() throws Exception {
        int databaseSizeBeforeUpdate = bailleursRepository.findAll().size();

        // Create the Bailleurs
        BailleursDTO bailleursDTO = bailleursMapper.toDto(bailleurs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailleursMockMvc.perform(put("/api/bailleurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bailleursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bailleurs in the database
        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBailleurs() throws Exception {
        // Initialize the database
        bailleursRepository.saveAndFlush(bailleurs);

        int databaseSizeBeforeDelete = bailleursRepository.findAll().size();

        // Delete the bailleurs
        restBailleursMockMvc.perform(delete("/api/bailleurs/{id}", bailleurs.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bailleurs> bailleursList = bailleursRepository.findAll();
        assertThat(bailleursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
