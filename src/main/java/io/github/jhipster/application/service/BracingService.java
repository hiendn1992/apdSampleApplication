package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Bracing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bracing.
 */
public interface BracingService {

    /**
     * Save a bracing.
     *
     * @param bracing the entity to save
     * @return the persisted entity
     */
    Bracing save(Bracing bracing);

    /**
     * Get all the bracings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Bracing> findAll(Pageable pageable);

    /**
     * Get the "id" bracing.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Bracing findOne(Long id);

    /**
     * Delete the "id" bracing.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
