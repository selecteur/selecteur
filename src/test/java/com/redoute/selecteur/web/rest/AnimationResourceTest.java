package com.redoute.selecteur.web.rest;

import com.redoute.selecteur.Application;
import com.redoute.selecteur.domain.Animation;
import com.redoute.selecteur.repository.AnimationRepository;

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
 * Test class for the AnimationResource REST controller.
 *
 * @see AnimationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnimationResourceTest {

    private static final String DEFAULT_PROVIDER_ANIMATION_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_PROVIDER_ANIMATION_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_PROVIDER_LIST_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_PROVIDER_LIST_CODE = "UPDATED_TEXT";

    @Inject
    private AnimationRepository animationRepository;

    private MockMvc restAnimationMockMvc;

    private Animation animation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnimationResource animationResource = new AnimationResource();
        ReflectionTestUtils.setField(animationResource, "animationRepository", animationRepository);
        this.restAnimationMockMvc = MockMvcBuilders.standaloneSetup(animationResource).build();
    }

    @Before
    public void initTest() {
        animation = new Animation();
        animation.setProviderAnimationCode(DEFAULT_PROVIDER_ANIMATION_CODE);
        animation.setProviderListCode(DEFAULT_PROVIDER_LIST_CODE);
    }

    @Test
    @Transactional
    public void createAnimation() throws Exception {
        int databaseSizeBeforeCreate = animationRepository.findAll().size();

        // Create the Animation
        restAnimationMockMvc.perform(post("/api/animations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animation)))
                .andExpect(status().isCreated());

        // Validate the Animation in the database
        List<Animation> animations = animationRepository.findAll();
        assertThat(animations).hasSize(databaseSizeBeforeCreate + 1);
        Animation testAnimation = animations.get(animations.size() - 1);
        assertThat(testAnimation.getProviderAnimationCode()).isEqualTo(DEFAULT_PROVIDER_ANIMATION_CODE);
        assertThat(testAnimation.getProviderListCode()).isEqualTo(DEFAULT_PROVIDER_LIST_CODE);
    }

    @Test
    @Transactional
    public void getAllAnimations() throws Exception {
        // Initialize the database
        animationRepository.saveAndFlush(animation);

        // Get all the animations
        restAnimationMockMvc.perform(get("/api/animations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(animation.getId().intValue())))
                .andExpect(jsonPath("$.[*].providerAnimationCode").value(hasItem(DEFAULT_PROVIDER_ANIMATION_CODE.toString())))
                .andExpect(jsonPath("$.[*].providerListCode").value(hasItem(DEFAULT_PROVIDER_LIST_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAnimation() throws Exception {
        // Initialize the database
        animationRepository.saveAndFlush(animation);

        // Get the animation
        restAnimationMockMvc.perform(get("/api/animations/{id}", animation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(animation.getId().intValue()))
            .andExpect(jsonPath("$.providerAnimationCode").value(DEFAULT_PROVIDER_ANIMATION_CODE.toString()))
            .andExpect(jsonPath("$.providerListCode").value(DEFAULT_PROVIDER_LIST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimation() throws Exception {
        // Get the animation
        restAnimationMockMvc.perform(get("/api/animations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimation() throws Exception {
        // Initialize the database
        animationRepository.saveAndFlush(animation);
		
		int databaseSizeBeforeUpdate = animationRepository.findAll().size();

        // Update the animation
        animation.setProviderAnimationCode(UPDATED_PROVIDER_ANIMATION_CODE);
        animation.setProviderListCode(UPDATED_PROVIDER_LIST_CODE);
        restAnimationMockMvc.perform(put("/api/animations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animation)))
                .andExpect(status().isOk());

        // Validate the Animation in the database
        List<Animation> animations = animationRepository.findAll();
        assertThat(animations).hasSize(databaseSizeBeforeUpdate);
        Animation testAnimation = animations.get(animations.size() - 1);
        assertThat(testAnimation.getProviderAnimationCode()).isEqualTo(UPDATED_PROVIDER_ANIMATION_CODE);
        assertThat(testAnimation.getProviderListCode()).isEqualTo(UPDATED_PROVIDER_LIST_CODE);
    }

    @Test
    @Transactional
    public void deleteAnimation() throws Exception {
        // Initialize the database
        animationRepository.saveAndFlush(animation);
		
		int databaseSizeBeforeDelete = animationRepository.findAll().size();

        // Get the animation
        restAnimationMockMvc.perform(delete("/api/animations/{id}", animation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Animation> animations = animationRepository.findAll();
        assertThat(animations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
