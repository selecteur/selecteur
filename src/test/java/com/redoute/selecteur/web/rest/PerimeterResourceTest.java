package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.Perimeter;
import com.redoute.selecteur.repository.PerimeterRepository;

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
 * Test class for the PerimeterResource REST controller.
 *
 * @see PerimeterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PerimeterResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";

    private static final DateTime DEFAULT_UPDATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_UPDATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_UPDATE_TIME_STR = dateTimeFormatter.print(DEFAULT_UPDATE_TIME);
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_TEMPLATE = false;
    private static final Boolean UPDATED_IS_TEMPLATE = true;

    private static final Integer DEFAULT_DOMAIN_INDEX = 0;
    private static final Integer UPDATED_DOMAIN_INDEX = 1;

    @Inject
    private PerimeterRepository perimeterRepository;

    private MockMvc restPerimeterMockMvc;

    private Perimeter perimeter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PerimeterResource perimeterResource = new PerimeterResource();
        ReflectionTestUtils.setField(perimeterResource, "perimeterRepository", perimeterRepository);
        this.restPerimeterMockMvc = MockMvcBuilders.standaloneSetup(perimeterResource).build();
    }

    @Before
    public void initTest() {
        perimeter = new Perimeter();
        perimeter.setAuthor(DEFAULT_AUTHOR);
        perimeter.setUpdateTime(DEFAULT_UPDATE_TIME);
        perimeter.setName(DEFAULT_NAME);
        perimeter.setState(DEFAULT_STATE);
        perimeter.setIsTemplate(DEFAULT_IS_TEMPLATE);
        perimeter.setDomainIndex(DEFAULT_DOMAIN_INDEX);
    }

    @Test
    @Transactional
    public void createPerimeter() throws Exception {
        int databaseSizeBeforeCreate = perimeterRepository.findAll().size();

        // Create the Perimeter
        restPerimeterMockMvc.perform(post("/api/perimeters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perimeter)))
                .andExpect(status().isCreated());

        // Validate the Perimeter in the database
        List<Perimeter> perimeters = perimeterRepository.findAll();
        assertThat(perimeters).hasSize(databaseSizeBeforeCreate + 1);
        Perimeter testPerimeter = perimeters.get(perimeters.size() - 1);
        assertThat(testPerimeter.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPerimeter.getUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPerimeter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerimeter.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPerimeter.getIsTemplate()).isEqualTo(DEFAULT_IS_TEMPLATE);
        assertThat(testPerimeter.getDomainIndex()).isEqualTo(DEFAULT_DOMAIN_INDEX);
    }

    @Test
    @Transactional
    public void getAllPerimeters() throws Exception {
        // Initialize the database
        perimeterRepository.saveAndFlush(perimeter);

        // Get all the perimeters
        restPerimeterMockMvc.perform(get("/api/perimeters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(perimeter.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].isTemplate").value(hasItem(DEFAULT_IS_TEMPLATE.booleanValue())))
                .andExpect(jsonPath("$.[*].domainIndex").value(hasItem(DEFAULT_DOMAIN_INDEX)));
    }

    @Test
    @Transactional
    public void getPerimeter() throws Exception {
        // Initialize the database
        perimeterRepository.saveAndFlush(perimeter);

        // Get the perimeter
        restPerimeterMockMvc.perform(get("/api/perimeters/{id}", perimeter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(perimeter.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME_STR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.isTemplate").value(DEFAULT_IS_TEMPLATE.booleanValue()))
            .andExpect(jsonPath("$.domainIndex").value(DEFAULT_DOMAIN_INDEX));
    }

    @Test
    @Transactional
    public void getNonExistingPerimeter() throws Exception {
        // Get the perimeter
        restPerimeterMockMvc.perform(get("/api/perimeters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerimeter() throws Exception {
        // Initialize the database
        perimeterRepository.saveAndFlush(perimeter);
		
		int databaseSizeBeforeUpdate = perimeterRepository.findAll().size();

        // Update the perimeter
        perimeter.setAuthor(UPDATED_AUTHOR);
        perimeter.setUpdateTime(UPDATED_UPDATE_TIME);
        perimeter.setName(UPDATED_NAME);
        perimeter.setState(UPDATED_STATE);
        perimeter.setIsTemplate(UPDATED_IS_TEMPLATE);
        perimeter.setDomainIndex(UPDATED_DOMAIN_INDEX);
        restPerimeterMockMvc.perform(put("/api/perimeters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perimeter)))
                .andExpect(status().isOk());

        // Validate the Perimeter in the database
        List<Perimeter> perimeters = perimeterRepository.findAll();
        assertThat(perimeters).hasSize(databaseSizeBeforeUpdate);
        Perimeter testPerimeter = perimeters.get(perimeters.size() - 1);
        assertThat(testPerimeter.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPerimeter.getUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPerimeter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerimeter.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPerimeter.getIsTemplate()).isEqualTo(UPDATED_IS_TEMPLATE);
        assertThat(testPerimeter.getDomainIndex()).isEqualTo(UPDATED_DOMAIN_INDEX);
    }

    @Test
    @Transactional
    public void deletePerimeter() throws Exception {
        // Initialize the database
        perimeterRepository.saveAndFlush(perimeter);
		
		int databaseSizeBeforeDelete = perimeterRepository.findAll().size();

        // Get the perimeter
        restPerimeterMockMvc.perform(delete("/api/perimeters/{id}", perimeter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Perimeter> perimeters = perimeterRepository.findAll();
        assertThat(perimeters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
