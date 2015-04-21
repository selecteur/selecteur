package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.Exposition;
import com.redoute.selecteur.repository.ExpositionRepository;
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
 * REST controller for managing Exposition.
 */
@RestController
@RequestMapping("/api")
public class ExpositionResource {

    private final Logger log = LoggerFactory.getLogger(ExpositionResource.class);

    @Inject
    private ExpositionRepository expositionRepository;

    /**
     * POST  /expositions -> Create a new exposition.
     */
    @RequestMapping(value = "/expositions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Exposition exposition) throws URISyntaxException {
        log.debug("REST request to save Exposition : {}", exposition);
        if (exposition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new exposition cannot already have an ID").build();
        }
        expositionRepository.save(exposition);
        return ResponseEntity.created(new URI("/api/expositions/" + exposition.getId())).build();
    }

    /**
     * PUT  /expositions -> Updates an existing exposition.
     */
    @RequestMapping(value = "/expositions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Exposition exposition) throws URISyntaxException {
        log.debug("REST request to update Exposition : {}", exposition);
        if (exposition.getId() == null) {
            return create(exposition);
        }
        expositionRepository.save(exposition);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /expositions -> get all the expositions.
     */
    @RequestMapping(value = "/expositions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Exposition>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Exposition> page = expositionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/expositions", offset, limit);
        return new ResponseEntity<List<Exposition>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /expositions/:id -> get the "id" exposition.
     */
    @RequestMapping(value = "/expositions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Exposition> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Exposition : {}", id);
        Exposition exposition = expositionRepository.findOne(id);
        if (exposition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exposition, HttpStatus.OK);
    }

    /**
     * DELETE  /expositions/:id -> delete the "id" exposition.
     */
    @RequestMapping(value = "/expositions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Exposition : {}", id);
        expositionRepository.delete(id);
    }
}
