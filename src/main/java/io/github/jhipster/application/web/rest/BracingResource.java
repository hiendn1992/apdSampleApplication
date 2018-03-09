package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Bracing;
import io.github.jhipster.application.service.BracingService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bracing.
 */
@RestController
@RequestMapping("/api")
public class BracingResource {

    private final Logger log = LoggerFactory.getLogger(BracingResource.class);

    private static final String ENTITY_NAME = "bracing";

    private final BracingService bracingService;

    public BracingResource(BracingService bracingService) {
        this.bracingService = bracingService;
    }

    /**
     * POST  /bracings : Create a new bracing.
     *
     * @param bracing the bracing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bracing, or with status 400 (Bad Request) if the bracing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bracings")
    @Timed
    public ResponseEntity<Bracing> createBracing(@RequestBody Bracing bracing) throws URISyntaxException {
        log.debug("REST request to save Bracing : {}", bracing);
        if (bracing.getId() != null) {
            throw new BadRequestAlertException("A new bracing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bracing result = bracingService.save(bracing);
        return ResponseEntity.created(new URI("/api/bracings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bracings : Updates an existing bracing.
     *
     * @param bracing the bracing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bracing,
     * or with status 400 (Bad Request) if the bracing is not valid,
     * or with status 500 (Internal Server Error) if the bracing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bracings")
    @Timed
    public ResponseEntity<Bracing> updateBracing(@RequestBody Bracing bracing) throws URISyntaxException {
        log.debug("REST request to update Bracing : {}", bracing);
        if (bracing.getId() == null) {
            return createBracing(bracing);
        }
        Bracing result = bracingService.save(bracing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bracing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bracings : get all the bracings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bracings in body
     */
    @GetMapping("/bracings")
    @Timed
    public ResponseEntity<List<Bracing>> getAllBracings(Pageable pageable) {
        log.debug("REST request to get a page of Bracings");
        Page<Bracing> page = bracingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bracings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bracings/:id : get the "id" bracing.
     *
     * @param id the id of the bracing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bracing, or with status 404 (Not Found)
     */
    @GetMapping("/bracings/{id}")
    @Timed
    public ResponseEntity<Bracing> getBracing(@PathVariable Long id) {
        log.debug("REST request to get Bracing : {}", id);
        Bracing bracing = bracingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bracing));
    }

    /**
     * DELETE  /bracings/:id : delete the "id" bracing.
     *
     * @param id the id of the bracing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bracings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBracing(@PathVariable Long id) {
        log.debug("REST request to delete Bracing : {}", id);
        bracingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
