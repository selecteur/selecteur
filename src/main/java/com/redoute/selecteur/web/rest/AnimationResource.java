package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.Animation;
import com.redoute.selecteur.repository.AnimationRepository;
import com.redoute.selecteur.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Animation.
 */
@RestController
@RequestMapping("/api")
public class AnimationResource {

    private final Logger log = LoggerFactory.getLogger(AnimationResource.class);

    @Inject
    private AnimationRepository animationRepository;

    /**
     * POST  /animations -> Create a new animation.
     */
    @RequestMapping(value = "/animations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Animation animation) throws URISyntaxException {
        log.debug("REST request to save Animation : {}", animation);
        if (animation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new animation cannot already have an ID").build();
        }
        animationRepository.save(animation);
        return ResponseEntity.created(new URI("/api/animations/" + animation.getId())).build();
    }

    /**
     * PUT  /animations -> Updates an existing animation.
     */
    @RequestMapping(value = "/animations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Animation animation) throws URISyntaxException {
        log.debug("REST request to update Animation : {}", animation);
        if (animation.getId() == null) {
            return create(animation);
        }
        animationRepository.save(animation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /animations -> get all the animations.
     */
    @RequestMapping(value = "/animations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Animation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Animation> page = animationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/animations", offset, limit);
        return new ResponseEntity<List<Animation>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /animations/:id -> get the "id" animation.
     */
    @RequestMapping(value = "/animations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Animation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Animation : {}", id);
        Animation animation = animationRepository.findOne(id);
        if (animation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(animation, HttpStatus.OK);
    }

    /**
     * DELETE  /animations/:id -> delete the "id" animation.
     */
    @RequestMapping(value = "/animations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Animation : {}", id);
        animationRepository.delete(id);
    }
}
