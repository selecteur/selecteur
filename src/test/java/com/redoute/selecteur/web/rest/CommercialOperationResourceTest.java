package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.CommercialOperation;
import com.redoute.selecteur.domain.DetailedCommercialOperation;
import com.redoute.selecteur.repository.CommercialOperationRepository;

import com.redoute.selecteur.service.CommercialOperationService;
import com.redoute.selecteur.web.rest.dto.DetailedCommercialOperationDTO;
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
 * Test class for the CommercialOperationResource REST controller.
 *
 * @see CommercialOperationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CommercialOperationResourceTest {

    private static final String DEFAULT_PROVIDER_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_PROVIDER_CODE = "UPDATED_TEXT";

    @Inject
    private CommercialOperationRepository commercialOperationRepository;

    @Inject
    private CommercialOperationService commercialOperationService;

    private MockMvc restCommercialOperationMockMvc;

    private CommercialOperation commercialOperation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommercialOperationResource commercialOperationResource = new CommercialOperationResource();
        ReflectionTestUtils.setField(commercialOperationResource, "commercialOperationService", commercialOperationService);
        this.restCommercialOperationMockMvc = MockMvcBuilders.standaloneSetup(commercialOperationResource).build();
    }

    @Before
    public void initTest() {
        commercialOperation = new CommercialOperation();
        commercialOperation.setProviderCode(DEFAULT_PROVIDER_CODE);
    }

    @Test
    @Transactional
    public void createCommercialOperation() throws Exception {
        int databaseSizeBeforeCreate = commercialOperationRepository.findAll().size();

        // Create the CommercialOperation
        restCommercialOperationMockMvc.perform(post("/api/commercialOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new DetailedCommercialOperationDTO(new DetailedCommercialOperation(commercialOperation)))))
                .andExpect(status().isCreated());

        // Validate the CommercialOperation in the database
        List<CommercialOperation> commercialOperations = commercialOperationRepository.findAll();
        assertThat(commercialOperations).hasSize(databaseSizeBeforeCreate + 1);
        CommercialOperation testCommercialOperation = commercialOperations.get(commercialOperations.size() - 1);
        assertThat(testCommercialOperation.getProviderCode()).isEqualTo(DEFAULT_PROVIDER_CODE);
    }

    @Test
    @Transactional
    public void getAllCommercialOperations() throws Exception {
        // Initialize the database
        commercialOperationRepository.saveAndFlush(commercialOperation);

        // Get all the commercialOperations
        restCommercialOperationMockMvc.perform(get("/api/commercialOperations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(commercialOperation.getId().intValue())))
                .andExpect(jsonPath("$.[*].providerCode").value(hasItem(DEFAULT_PROVIDER_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCommercialOperation() throws Exception {
        // Initialize the database
        commercialOperationRepository.saveAndFlush(commercialOperation);

        // Get the commercialOperation
        restCommercialOperationMockMvc.perform(get("/api/commercialOperations/{id}", commercialOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(commercialOperation.getId().intValue()))
            .andExpect(jsonPath("$.providerCode").value(DEFAULT_PROVIDER_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommercialOperation() throws Exception {
        // Get the commercialOperation
        restCommercialOperationMockMvc.perform(get("/api/commercialOperations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialOperation() throws Exception {
        // Initialize the database
        commercialOperationRepository.saveAndFlush(commercialOperation);
		
		int databaseSizeBeforeUpdate = commercialOperationRepository.findAll().size();

        // Update the commercialOperation
        commercialOperation.setProviderCode(UPDATED_PROVIDER_CODE);
        restCommercialOperationMockMvc.perform(put("/api/commercialOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commercialOperation)))
                .andExpect(status().isOk());

        // Validate the CommercialOperation in the database
        List<CommercialOperation> commercialOperations = commercialOperationRepository.findAll();
        assertThat(commercialOperations).hasSize(databaseSizeBeforeUpdate);
        CommercialOperation testCommercialOperation = commercialOperations.get(commercialOperations.size() - 1);
        assertThat(testCommercialOperation.getProviderCode()).isEqualTo(UPDATED_PROVIDER_CODE);
    }

    @Test
    @Transactional
    public void deleteCommercialOperation() throws Exception {
        // Initialize the database
        commercialOperationRepository.saveAndFlush(commercialOperation);
		
		int databaseSizeBeforeDelete = commercialOperationRepository.findAll().size();

        // Get the commercialOperation
        restCommercialOperationMockMvc.perform(delete("/api/commercialOperations/{id}", commercialOperation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialOperation> commercialOperations = commercialOperationRepository.findAll();
        assertThat(commercialOperations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
