package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.Exposition;
import com.redoute.selecteur.repository.ExpositionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExpositionResource REST controller.
 *
 * @see ExpositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExpositionResourceTest {


    @Inject
    private ExpositionRepository expositionRepository;

    private MockMvc restExpositionMockMvc;

    private Exposition exposition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExpositionResource expositionResource = new ExpositionResource();
        ReflectionTestUtils.setField(expositionResource, "expositionRepository", expositionRepository);
        this.restExpositionMockMvc = MockMvcBuilders.standaloneSetup(expositionResource).build();
    }

    @Before
    public void initTest() {
        exposition = new Exposition();
    }

    @Test
    @Transactional
    public void createExposition() throws Exception {
        int databaseSizeBeforeCreate = expositionRepository.findAll().size();

        // Create the Exposition
        restExpositionMockMvc.perform(post("/api/expositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exposition)))
                .andExpect(status().isCreated());

        // Validate the Exposition in the database
        List<Exposition> expositions = expositionRepository.findAll();
        assertThat(expositions).hasSize(databaseSizeBeforeCreate + 1);
        Exposition testExposition = expositions.get(expositions.size() - 1);
    }

    @Test
    @Transactional
    public void getAllExpositions() throws Exception {
        // Initialize the database
        expositionRepository.saveAndFlush(exposition);

        // Get all the expositions
        restExpositionMockMvc.perform(get("/api/expositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exposition.getId().intValue())));
    }

    @Test
    @Transactional
    public void getExposition() throws Exception {
        // Initialize the database
        expositionRepository.saveAndFlush(exposition);

        // Get the exposition
        restExpositionMockMvc.perform(get("/api/expositions/{id}", exposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(exposition.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExposition() throws Exception {
        // Get the exposition
        restExpositionMockMvc.perform(get("/api/expositions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExposition() throws Exception {
        // Initialize the database
        expositionRepository.saveAndFlush(exposition);
		
		int databaseSizeBeforeUpdate = expositionRepository.findAll().size();

        // Update the exposition
        restExpositionMockMvc.perform(put("/api/expositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exposition)))
                .andExpect(status().isOk());

        // Validate the Exposition in the database
        List<Exposition> expositions = expositionRepository.findAll();
        assertThat(expositions).hasSize(databaseSizeBeforeUpdate);
        Exposition testExposition = expositions.get(expositions.size() - 1);
    }

    @Test
    @Transactional
    public void deleteExposition() throws Exception {
        // Initialize the database
        expositionRepository.saveAndFlush(exposition);
		
		int databaseSizeBeforeDelete = expositionRepository.findAll().size();

        // Get the exposition
        restExpositionMockMvc.perform(delete("/api/expositions/{id}", exposition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Exposition> expositions = expositionRepository.findAll();
        assertThat(expositions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
