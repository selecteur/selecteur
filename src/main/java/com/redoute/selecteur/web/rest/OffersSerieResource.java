package com.redoute.selecteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redoute.selecteur.domain.OffersSerie;
import com.redoute.selecteur.repository.OffersSerieRepository;
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
 * REST controller for managing OffersSerie.
 */
@RestController
@RequestMapping("/api")
public class OffersSerieResource {

    private final Logger log = LoggerFactory.getLogger(OffersSerieResource.class);

    @Inject
    private OffersSerieRepository offersSerieRepository;

    /**
     * POST  /offersSeries -> Create a new offersSerie.
     */
    @RequestMapping(value = "/offersSeries",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody OffersSerie offersSerie) throws URISyntaxException {
        log.debug("REST request to save OffersSerie : {}", offersSerie);
        if (offersSerie.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new offersSerie cannot already have an ID").build();
        }
        offersSerieRepository.save(offersSerie);
        return ResponseEntity.created(new URI("/api/offersSeries/" + offersSerie.getId())).build();
    }

    /**
     * PUT  /offersSeries -> Updates an existing offersSerie.
     */
    @RequestMapping(value = "/offersSeries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody OffersSerie offersSerie) throws URISyntaxException {
        log.debug("REST request to update OffersSerie : {}", offersSerie);
        if (offersSerie.getId() == null) {
            return create(offersSerie);
        }
        offersSerieRepository.save(offersSerie);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /offersSeries -> get all the offersSeries.
     */
    @RequestMapping(value = "/offersSeries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OffersSerie>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OffersSerie> page = offersSerieRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offersSeries", offset, limit);
        return new ResponseEntity<List<OffersSerie>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /offersSeries/:id -> get the "id" offersSerie.
     */
    @RequestMapping(value = "/offersSeries/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OffersSerie> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get OffersSerie : {}", id);
        OffersSerie offersSerie = offersSerieRepository.findOne(id);
        if (offersSerie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(offersSerie, HttpStatus.OK);
    }

    /**
     * DELETE  /offersSeries/:id -> delete the "id" offersSerie.
     */
    @RequestMapping(value = "/offersSeries/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OffersSerie : {}", id);
        offersSerieRepository.delete(id);
    }
}
