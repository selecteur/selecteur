package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.OffersSerie;
import com.redoute.selecteur.repository.OffersSerieRepository;

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
 * Test class for the OffersSerieResource REST controller.
 *
 * @see OffersSerieResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OffersSerieResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";

    private static final DateTime DEFAULT_UPDATE_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_UPDATE_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_UPDATE_DATE_STR = dateTimeFormatter.print(DEFAULT_UPDATE_DATE);
    private static final String DEFAULT_QUERY = "SAMPLE_TEXT";
    private static final String UPDATED_QUERY = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Long DEFAULT_OFFERS_IDS = 0L;
    private static final Long UPDATED_OFFERS_IDS = 1L;
    private static final String DEFAULT_INPUT_ACTION = "SAMPLE_TEXT";
    private static final String UPDATED_INPUT_ACTION = "UPDATED_TEXT";
    private static final String DEFAULT_OUTPUT_ACTION = "SAMPLE_TEXT";
    private static final String UPDATED_OUTPUT_ACTION = "UPDATED_TEXT";
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";

    @Inject
    private OffersSerieRepository offersSerieRepository;

    private MockMvc restOffersSerieMockMvc;

    private OffersSerie offersSerie;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OffersSerieResource offersSerieResource = new OffersSerieResource();
        ReflectionTestUtils.setField(offersSerieResource, "offersSerieRepository", offersSerieRepository);
        this.restOffersSerieMockMvc = MockMvcBuilders.standaloneSetup(offersSerieResource).build();
    }

    @Before
    public void initTest() {
        offersSerie = new OffersSerie();
        offersSerie.setAuthor(DEFAULT_AUTHOR);
        offersSerie.setUpdateDate(DEFAULT_UPDATE_DATE);
        offersSerie.setQuery(DEFAULT_QUERY);
        offersSerie.setName(DEFAULT_NAME);
        offersSerie.setOffersIds(DEFAULT_OFFERS_IDS);
        offersSerie.setInputAction(DEFAULT_INPUT_ACTION);
        offersSerie.setOutputAction(DEFAULT_OUTPUT_ACTION);
        offersSerie.setState(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createOffersSerie() throws Exception {
        int databaseSizeBeforeCreate = offersSerieRepository.findAll().size();

        // Create the OffersSerie
        restOffersSerieMockMvc.perform(post("/api/offersSeries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offersSerie)))
                .andExpect(status().isCreated());

        // Validate the OffersSerie in the database
        List<OffersSerie> offersSeries = offersSerieRepository.findAll();
        assertThat(offersSeries).hasSize(databaseSizeBeforeCreate + 1);
        OffersSerie testOffersSerie = offersSeries.get(offersSeries.size() - 1);
        assertThat(testOffersSerie.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testOffersSerie.getUpdateDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testOffersSerie.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testOffersSerie.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffersSerie.getOffersIds()).isEqualTo(DEFAULT_OFFERS_IDS);
        assertThat(testOffersSerie.getInputAction()).isEqualTo(DEFAULT_INPUT_ACTION);
        assertThat(testOffersSerie.getOutputAction()).isEqualTo(DEFAULT_OUTPUT_ACTION);
        assertThat(testOffersSerie.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void getAllOffersSeries() throws Exception {
        // Initialize the database
        offersSerieRepository.saveAndFlush(offersSerie);

        // Get all the offersSeries
        restOffersSerieMockMvc.perform(get("/api/offersSeries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(offersSerie.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].offersIds").value(hasItem(DEFAULT_OFFERS_IDS.intValue())))
                .andExpect(jsonPath("$.[*].inputAction").value(hasItem(DEFAULT_INPUT_ACTION.toString())))
                .andExpect(jsonPath("$.[*].outputAction").value(hasItem(DEFAULT_OUTPUT_ACTION.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getOffersSerie() throws Exception {
        // Initialize the database
        offersSerieRepository.saveAndFlush(offersSerie);

        // Get the offersSerie
        restOffersSerieMockMvc.perform(get("/api/offersSeries/{id}", offersSerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(offersSerie.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE_STR))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.offersIds").value(DEFAULT_OFFERS_IDS.intValue()))
            .andExpect(jsonPath("$.inputAction").value(DEFAULT_INPUT_ACTION.toString()))
            .andExpect(jsonPath("$.outputAction").value(DEFAULT_OUTPUT_ACTION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOffersSerie() throws Exception {
        // Get the offersSerie
        restOffersSerieMockMvc.perform(get("/api/offersSeries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffersSerie() throws Exception {
        // Initialize the database
        offersSerieRepository.saveAndFlush(offersSerie);
		
		int databaseSizeBeforeUpdate = offersSerieRepository.findAll().size();

        // Update the offersSerie
        offersSerie.setAuthor(UPDATED_AUTHOR);
        offersSerie.setUpdateDate(UPDATED_UPDATE_DATE);
        offersSerie.setQuery(UPDATED_QUERY);
        offersSerie.setName(UPDATED_NAME);
        offersSerie.setOffersIds(UPDATED_OFFERS_IDS);
        offersSerie.setInputAction(UPDATED_INPUT_ACTION);
        offersSerie.setOutputAction(UPDATED_OUTPUT_ACTION);
        offersSerie.setState(UPDATED_STATE);
        restOffersSerieMockMvc.perform(put("/api/offersSeries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offersSerie)))
                .andExpect(status().isOk());

        // Validate the OffersSerie in the database
        List<OffersSerie> offersSeries = offersSerieRepository.findAll();
        assertThat(offersSeries).hasSize(databaseSizeBeforeUpdate);
        OffersSerie testOffersSerie = offersSeries.get(offersSeries.size() - 1);
        assertThat(testOffersSerie.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testOffersSerie.getUpdateDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testOffersSerie.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testOffersSerie.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffersSerie.getOffersIds()).isEqualTo(UPDATED_OFFERS_IDS);
        assertThat(testOffersSerie.getInputAction()).isEqualTo(UPDATED_INPUT_ACTION);
        assertThat(testOffersSerie.getOutputAction()).isEqualTo(UPDATED_OUTPUT_ACTION);
        assertThat(testOffersSerie.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void deleteOffersSerie() throws Exception {
        // Initialize the database
        offersSerieRepository.saveAndFlush(offersSerie);
		
		int databaseSizeBeforeDelete = offersSerieRepository.findAll().size();

        // Get the offersSerie
        restOffersSerieMockMvc.perform(delete("/api/offersSeries/{id}", offersSerie.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OffersSerie> offersSeries = offersSerieRepository.findAll();
        assertThat(offersSeries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
