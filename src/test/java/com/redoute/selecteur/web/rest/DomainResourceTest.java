package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.Domain;
import com.redoute.selecteur.repository.DomainRepository;

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
 * Test class for the DomainResource REST controller.
 *
 * @see DomainResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DomainResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";

    private static final DateTime DEFAULT_UPDATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_UPDATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_UPDATE_TIME_STR = dateTimeFormatter.print(DEFAULT_UPDATE_TIME);

    @Inject
    private DomainRepository domainRepository;

    private MockMvc restDomainMockMvc;

    private Domain domain;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomainResource domainResource = new DomainResource();
        ReflectionTestUtils.setField(domainResource, "domainRepository", domainRepository);
        this.restDomainMockMvc = MockMvcBuilders.standaloneSetup(domainResource).build();
    }

    @Before
    public void initTest() {
        domain = new Domain();
        domain.setAuthor(DEFAULT_AUTHOR);
        domain.setUpdateTime(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createDomain() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain
        restDomainMockMvc.perform(post("/api/domains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domain)))
                .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeCreate + 1);
        Domain testDomain = domains.get(domains.size() - 1);
        assertThat(testDomain.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testDomain.getUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllDomains() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get all the domains
        restDomainMockMvc.perform(get("/api/domains"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME_STR)));
    }

    @Test
    @Transactional
    public void getDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(domain.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDomain() throws Exception {
        // Get the domain
        restDomainMockMvc.perform(get("/api/domains/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);
		
		int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain
        domain.setAuthor(UPDATED_AUTHOR);
        domain.setUpdateTime(UPDATED_UPDATE_TIME);
        restDomainMockMvc.perform(put("/api/domains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domain)))
                .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domains.get(domains.size() - 1);
        assertThat(testDomain.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testDomain.getUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void deleteDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);
		
		int databaseSizeBeforeDelete = domainRepository.findAll().size();

        // Get the domain
        restDomainMockMvc.perform(delete("/api/domains/{id}", domain.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Domain> domains = domainRepository.findAll();
        assertThat(domains).hasSize(databaseSizeBeforeDelete - 1);
    }
}
