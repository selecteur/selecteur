package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.CommercialOperation;
import com.redoute.selecteur.domain.DetailedCommercialOperation;
import com.redoute.selecteur.domain.Perimeter;
import com.redoute.selecteur.repository.CommercialOperationRepository;
import com.redoute.selecteur.repository.PerimeterRepository;
import com.redoute.selecteur.service.CommercialOperationService;
import com.redoute.selecteur.web.rest.dto.DetailedCommercialOperationDTO;
import com.redoute.selecteur.web.rest.dto.PerimeterDTO;
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
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing CommercialOperation.
 */
@RestController
@RequestMapping("/api")
public class CommercialOperationResource {

    private final Logger log = LoggerFactory.getLogger(CommercialOperationResource.class);

    @Inject
    private PerimeterRepository perimeterRepository;

    @Inject
    private CommercialOperationService commercialOperationService;

    /**
     * POST  /commercialOperations -> Create a new commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody DetailedCommercialOperationDTO detailedCommercialOperationDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialOperation : {}", detailedCommercialOperationDTO);
        if (detailedCommercialOperationDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new commercialOperation cannot already have an ID").build();
        }
        DetailedCommercialOperation detailedCommercialOperation = detailedCommercialOperationDTO.getEntity();

        commercialOperationService.save(detailedCommercialOperation);
        return ResponseEntity.created(new URI("/api/commercialOperations/" + detailedCommercialOperation.getId())).build();
    }

    /**
     * PUT  /commercialOperations -> Updates an existing commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody DetailedCommercialOperationDTO detailedCommercialOperationDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialOperation : {}", detailedCommercialOperationDTO);
        if (detailedCommercialOperationDTO.getId() == null) {
            return create(detailedCommercialOperationDTO);
        }
        DetailedCommercialOperation detailedCommercialOperation = detailedCommercialOperationDTO.getEntity();
        commercialOperationService.save(detailedCommercialOperation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /commercialOperations -> get all the commercialOperations.
     */
    @RequestMapping(value = "/commercialOperations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DetailedCommercialOperationDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<DetailedCommercialOperation> page = commercialOperationService.findByCriteria(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercialOperations", offset, limit);

        // Converts entities to DTOs
        List<DetailedCommercialOperationDTO> content = new ArrayList<>();
        for (DetailedCommercialOperation detailedCommercialOperation : page.getContent()) {
            content.add(new DetailedCommercialOperationDTO(detailedCommercialOperation));
        }

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    /**
     * GET  /commercialOperations/:id -> get the "id" commercialOperation.
     */
    @RequestMapping(value = "/commercialOperations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DetailedCommercialOperationDTO> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CommercialOperation : {}", id);
        DetailedCommercialOperation commercialOperation = commercialOperationService.findById(id);
        if (commercialOperation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DetailedCommercialOperationDTO(commercialOperation), HttpStatus.OK);
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
        commercialOperationService.delete(id);
    }

    @RequestMapping(value = "/commercialOperations/{id}/perimeters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PerimeterDTO>> getPerimeters(@PathVariable Long id, @RequestParam(value = "page" , required = false) Integer offset,
                              @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

        log.debug("REST request to get CommercialOperation's perimeters : {}", id);
        Page<Perimeter> page = perimeterRepository.findByDomainCommercialOperationId(id, PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercialOperations/" + id + "/perimeters", offset, limit);

        // Converts entities to DTOs
        List<PerimeterDTO> content = new ArrayList<>();
        for (Perimeter perimeter : page.getContent()) {
            content.add(new PerimeterDTO(perimeter));
        }

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
