package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.BracingService;
import io.github.jhipster.application.domain.Bracing;
import io.github.jhipster.application.repository.BracingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bracing.
 */
@Service
@Transactional
public class BracingServiceImpl implements BracingService {

    private final Logger log = LoggerFactory.getLogger(BracingServiceImpl.class);

    private final BracingRepository bracingRepository;

    public BracingServiceImpl(BracingRepository bracingRepository) {
        this.bracingRepository = bracingRepository;
    }

    /**
     * Save a bracing.
     *
     * @param bracing the entity to save
     * @return the persisted entity
     */
    @Override
    public Bracing save(Bracing bracing) {
        log.debug("Request to save Bracing : {}", bracing);
        return bracingRepository.save(bracing);
    }

    /**
     * Get all the bracings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bracing> findAll(Pageable pageable) {
        log.debug("Request to get all Bracings");
        return bracingRepository.findAll(pageable);
    }

    /**
     * Get one bracing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Bracing findOne(Long id) {
        log.debug("Request to get Bracing : {}", id);
        return bracingRepository.findOne(id);
    }

    /**
     * Delete the bracing by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bracing : {}", id);
        bracingRepository.delete(id);
    }
}
