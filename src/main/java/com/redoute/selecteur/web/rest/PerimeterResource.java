package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.Perimeter;
import com.redoute.selecteur.repository.PerimeterRepository;
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
 * REST controller for managing Perimeter.
 */
@RestController
@RequestMapping("/api")
public class PerimeterResource {

    private final Logger log = LoggerFactory.getLogger(PerimeterResource.class);

    @Inject
    private PerimeterRepository perimeterRepository;

    /**
     * POST  /perimeters -> Create a new perimeter.
     */
    @RequestMapping(value = "/perimeters",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Perimeter perimeter) throws URISyntaxException {
        log.debug("REST request to save Perimeter : {}", perimeter);
        if (perimeter.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new perimeter cannot already have an ID").build();
        }
        perimeterRepository.save(perimeter);
        return ResponseEntity.created(new URI("/api/perimeters/" + perimeter.getId())).build();
    }

    /**
     * PUT  /perimeters -> Updates an existing perimeter.
     */
    @RequestMapping(value = "/perimeters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Perimeter perimeter) throws URISyntaxException {
        log.debug("REST request to update Perimeter : {}", perimeter);
        if (perimeter.getId() == null) {
            return create(perimeter);
        }
        perimeterRepository.save(perimeter);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /perimeters -> get all the perimeters.
     */
    @RequestMapping(value = "/perimeters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Perimeter>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Perimeter> page = perimeterRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/perimeters", offset, limit);
        return new ResponseEntity<List<Perimeter>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /perimeters/:id -> get the "id" perimeter.
     */
    @RequestMapping(value = "/perimeters/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Perimeter> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Perimeter : {}", id);
        Perimeter perimeter = perimeterRepository.findOne(id);
        if (perimeter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perimeter, HttpStatus.OK);
    }

    /**
     * DELETE  /perimeters/:id -> delete the "id" perimeter.
     */
    @RequestMapping(value = "/perimeters/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Perimeter : {}", id);
        perimeterRepository.delete(id);
    }
}
