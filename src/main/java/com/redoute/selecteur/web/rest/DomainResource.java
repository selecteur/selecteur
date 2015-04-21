package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.Domain;
import com.redoute.selecteur.repository.DomainRepository;
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
 * REST controller for managing Domain.
 */
@RestController
@RequestMapping("/api")
public class DomainResource {

    private final Logger log = LoggerFactory.getLogger(DomainResource.class);

    @Inject
    private DomainRepository domainRepository;

    /**
     * POST  /domains -> Create a new domain.
     */
    @RequestMapping(value = "/domains",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to save Domain : {}", domain);
        if (domain.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new domain cannot already have an ID").build();
        }
        domainRepository.save(domain);
        return ResponseEntity.created(new URI("/api/domains/" + domain.getId())).build();
    }

    /**
     * PUT  /domains -> Updates an existing domain.
     */
    @RequestMapping(value = "/domains",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to update Domain : {}", domain);
        if (domain.getId() == null) {
            return create(domain);
        }
        domainRepository.save(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /domains -> get all the domains.
     */
    @RequestMapping(value = "/domains",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Domain>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Domain> page = domainRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domains", offset, limit);
        return new ResponseEntity<List<Domain>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /domains/:id -> get the "id" domain.
     */
    @RequestMapping(value = "/domains/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domain> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Domain : {}", id);
        Domain domain = domainRepository.findOne(id);
        if (domain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(domain, HttpStatus.OK);
    }

    /**
     * DELETE  /domains/:id -> delete the "id" domain.
     */
    @RequestMapping(value = "/domains/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Domain : {}", id);
        domainRepository.delete(id);
    }
}
