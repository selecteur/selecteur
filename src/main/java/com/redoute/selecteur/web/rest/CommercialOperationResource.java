package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.CommercialOperation;
import com.redoute.selecteur.repository.CommercialOperationRepository;
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
 * REST controller for managing CommercialOperation.
 */
@RestController
@RequestMapping("/api")
public class CommercialOperationResource {

    private final Logger log = LoggerFactory.getLogger(CommercialOperationResource.class);

    @Inject
    private CommercialOperationRepository commercialOperationRepository;

    /**
     * POST  /commercialOperations -> Create a new commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody CommercialOperation commercialOperation) throws URISyntaxException {
        log.debug("REST request to save CommercialOperation : {}", commercialOperation);
        if (commercialOperation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commercialOperation cannot already have an ID").build();
        }
        commercialOperationRepository.save(commercialOperation);
        return ResponseEntity.created(new URI("/api/commercialOperations/" + commercialOperation.getId())).build();
    }

    /**
     * PUT  /commercialOperations -> Updates an existing commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody CommercialOperation commercialOperation) throws URISyntaxException {
        log.debug("REST request to update CommercialOperation : {}", commercialOperation);
        if (commercialOperation.getId() == null) {
            return create(commercialOperation);
        }
        commercialOperationRepository.save(commercialOperation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /commercialOperations -> get all the commercialOperations.
     */
    @RequestMapping(value = "/commercialOperations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CommercialOperation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<CommercialOperation> page = commercialOperationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercialOperations", offset, limit);
        return new ResponseEntity<List<CommercialOperation>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commercialOperations/:id -> get the "id" commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CommercialOperation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CommercialOperation : {}", id);
        CommercialOperation commercialOperation = commercialOperationRepository.findOne(id);
        if (commercialOperation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commercialOperation, HttpStatus.OK);
    }

    /**
     * DELETE  /commercialOperations/:id -> delete the "id" commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CommercialOperation : {}", id);
        commercialOperationRepository.delete(id);
    }
}
