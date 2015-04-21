package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.Condition;
import com.redoute.selecteur.repository.ConditionRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConditionResource REST controller.
 *
 * @see ConditionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConditionResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";

    private static final DateTime DEFAULT_UPDATE_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_UPDATE_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_UPDATE_DATE_STR = dateTimeFormatter.print(DEFAULT_UPDATE_DATE);
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";
    private static final String DEFAULT_QUERY = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY = "UPDATED_TEXT";

    private static final Integer DEFAULT_NB_OFFER = 0;
    private static final Integer UPDATED_NB_OFFER = 1;

    private static final Integer DEFAULT_NB_ITEM = 0;
    private static final Integer UPDATED_NB_ITEM = 1;

    private static final Integer DEFAULT_NB_PRODUCT = 0;
    private static final Integer UPDATED_NB_PRODUCT = 1;

    @Inject
    private ConditionRepository conditionRepository;

    private MockMvc restConditionMockMvc;

    private Condition condition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConditionResource conditionResource = new ConditionResource();
        ReflectionTestUtils.setField(conditionResource, "conditionRepository", conditionRepository);
        this.restConditionMockMvc = MockMvcBuilders.standaloneSetup(conditionResource).build();
    }

    @Before
    public void initTest() {
        condition = new Condition();
        condition.setAuthor(DEFAULT_AUTHOR);
        condition.setUpdateDate(DEFAULT_UPDATE_DATE);
        condition.setState(DEFAULT_STATE);
        condition.setQuery(DEFAULT_QUERY);
        condition.setNbOffer(DEFAULT_NB_OFFER);
        condition.setNbItem(DEFAULT_NB_ITEM);
        condition.setNbProduct(DEFAULT_NB_PRODUCT);
    }

    @Test
    @Transactional
    public void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // Create the Condition
        restConditionMockMvc.perform(post("/api/conditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condition)))
                .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditions.get(conditions.size() - 1);
        assertThat(testCondition.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testCondition.getUpdateDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testCondition.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCondition.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testCondition.getNbOffer()).isEqualTo(DEFAULT_NB_OFFER);
        assertThat(testCondition.getNbItem()).isEqualTo(DEFAULT_NB_ITEM);
        assertThat(testCondition.getNbProduct()).isEqualTo(DEFAULT_NB_PRODUCT);
    }

    @Test
    @Transactional
    public void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditions
        restConditionMockMvc.perform(get("/api/conditions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())))
                .andExpect(jsonPath("$.[*].nbOffer").value(hasItem(DEFAULT_NB_OFFER)))
                .andExpect(jsonPath("$.[*].nbItem").value(hasItem(DEFAULT_NB_ITEM)))
                .andExpect(jsonPath("$.[*].nbProduct").value(hasItem(DEFAULT_NB_PRODUCT)));
    }

    @Test
    @Transactional
    public void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE_STR))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()))
            .andExpect(jsonPath("$.nbOffer").value(DEFAULT_NB_OFFER))
            .andExpect(jsonPath("$.nbItem").value(DEFAULT_NB_ITEM))
            .andExpect(jsonPath("$.nbProduct").value(DEFAULT_NB_PRODUCT));
    }

    @Test
    @Transactional
    public void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);
		
		int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        condition.setAuthor(UPDATED_AUTHOR);
        condition.setUpdateDate(UPDATED_UPDATE_DATE);
        condition.setState(UPDATED_STATE);
        condition.setQuery(UPDATED_QUERY);
        condition.setNbOffer(UPDATED_NB_OFFER);
        condition.setNbItem(UPDATED_NB_ITEM);
        condition.setNbProduct(UPDATED_NB_PRODUCT);
        restConditionMockMvc.perform(put("/api/conditions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condition)))
                .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditions.get(conditions.size() - 1);
        assertThat(testCondition.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testCondition.getUpdateDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testCondition.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCondition.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testCondition.getNbOffer()).isEqualTo(UPDATED_NB_OFFER);
        assertThat(testCondition.getNbItem()).isEqualTo(UPDATED_NB_ITEM);
        assertThat(testCondition.getNbProduct()).isEqualTo(UPDATED_NB_PRODUCT);
    }

    @Test
    @Transactional
    public void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);
		
		int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Get the condition
        restConditionMockMvc.perform(delete("/api/conditions/{id}", condition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Condition> conditions = conditionRepository.findAll();
        assertThat(conditions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
