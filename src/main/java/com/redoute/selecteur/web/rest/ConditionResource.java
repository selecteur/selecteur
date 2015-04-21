package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.Condition;
import com.redoute.selecteur.repository.ConditionRepository;
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
 * REST controller for managing Condition.
 */
@RestController
@RequestMapping("/api")
public class ConditionResource {

    private final Logger log = LoggerFactory.getLogger(ConditionResource.class);

    @Inject
    private ConditionRepository conditionRepository;

    /**
     * POST  /conditions -> Create a new condition.
     */
    @RequestMapping(value = "/conditions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Condition condition) throws URISyntaxException {
        log.debug("REST request to save Condition : {}", condition);
        if (condition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new condition cannot already have an ID").build();
        }
        conditionRepository.save(condition);
        return ResponseEntity.created(new URI("/api/conditions/" + condition.getId())).build();
    }

    /**
     * PUT  /conditions -> Updates an existing condition.
     */
    @RequestMapping(value = "/conditions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Condition condition) throws URISyntaxException {
        log.debug("REST request to update Condition : {}", condition);
        if (condition.getId() == null) {
            return create(condition);
        }
        conditionRepository.save(condition);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /conditions -> get all the conditions.
     */
    @RequestMapping(value = "/conditions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Condition>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Condition> page = conditionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conditions", offset, limit);
        return new ResponseEntity<List<Condition>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conditions/:id -> get the "id" condition.
     */
    @RequestMapping(value = "/conditions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Condition> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Condition : {}", id);
        Condition condition = conditionRepository.findOne(id);
        if (condition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(condition, HttpStatus.OK);
    }

    /**
     * DELETE  /conditions/:id -> delete the "id" condition.
     */
    @RequestMapping(value = "/conditions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Condition : {}", id);
        conditionRepository.delete(id);
    }
}
