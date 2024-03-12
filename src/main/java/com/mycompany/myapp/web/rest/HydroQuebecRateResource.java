package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HydroQuebecRate;
import com.mycompany.myapp.repository.HydroQuebecRateRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.HydroQuebecRate}.
 */
@RestController
@RequestMapping("/api/hydro-quebec-rates")
@Transactional
public class HydroQuebecRateResource {

    private final Logger log = LoggerFactory.getLogger(HydroQuebecRateResource.class);

    private static final String ENTITY_NAME = "hydroQuebecRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HydroQuebecRateRepository hydroQuebecRateRepository;

    public HydroQuebecRateResource(HydroQuebecRateRepository hydroQuebecRateRepository) {
        this.hydroQuebecRateRepository = hydroQuebecRateRepository;
    }

    /**
     * {@code POST  /hydro-quebec-rates} : Create a new hydroQuebecRate.
     *
     * @param hydroQuebecRate the hydroQuebecRate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hydroQuebecRate, or with status {@code 400 (Bad Request)} if the hydroQuebecRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HydroQuebecRate> createHydroQuebecRate(@RequestBody HydroQuebecRate hydroQuebecRate) throws URISyntaxException {
        log.debug("REST request to save HydroQuebecRate : {}", hydroQuebecRate);
        if (hydroQuebecRate.getId() != null) {
            throw new BadRequestAlertException("A new hydroQuebecRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HydroQuebecRate result = hydroQuebecRateRepository.save(hydroQuebecRate);
        return ResponseEntity
            .created(new URI("/api/hydro-quebec-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hydro-quebec-rates/:id} : Updates an existing hydroQuebecRate.
     *
     * @param id the id of the hydroQuebecRate to save.
     * @param hydroQuebecRate the hydroQuebecRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hydroQuebecRate,
     * or with status {@code 400 (Bad Request)} if the hydroQuebecRate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hydroQuebecRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HydroQuebecRate> updateHydroQuebecRate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HydroQuebecRate hydroQuebecRate
    ) throws URISyntaxException {
        log.debug("REST request to update HydroQuebecRate : {}, {}", id, hydroQuebecRate);
        if (hydroQuebecRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hydroQuebecRate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hydroQuebecRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HydroQuebecRate result = hydroQuebecRateRepository.save(hydroQuebecRate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hydroQuebecRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hydro-quebec-rates/:id} : Partial updates given fields of an existing hydroQuebecRate, field will ignore if it is null
     *
     * @param id the id of the hydroQuebecRate to save.
     * @param hydroQuebecRate the hydroQuebecRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hydroQuebecRate,
     * or with status {@code 400 (Bad Request)} if the hydroQuebecRate is not valid,
     * or with status {@code 404 (Not Found)} if the hydroQuebecRate is not found,
     * or with status {@code 500 (Internal Server Error)} if the hydroQuebecRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HydroQuebecRate> partialUpdateHydroQuebecRate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HydroQuebecRate hydroQuebecRate
    ) throws URISyntaxException {
        log.debug("REST request to partial update HydroQuebecRate partially : {}, {}", id, hydroQuebecRate);
        if (hydroQuebecRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hydroQuebecRate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hydroQuebecRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HydroQuebecRate> result = hydroQuebecRateRepository
            .findById(hydroQuebecRate.getId())
            .map(existingHydroQuebecRate -> {
                if (hydroQuebecRate.getName() != null) {
                    existingHydroQuebecRate.setName(hydroQuebecRate.getName());
                }

                return existingHydroQuebecRate;
            })
            .map(hydroQuebecRateRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hydroQuebecRate.getId().toString())
        );
    }

    /**
     * {@code GET  /hydro-quebec-rates} : get all the hydroQuebecRates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hydroQuebecRates in body.
     */
    @GetMapping("")
    public List<HydroQuebecRate> getAllHydroQuebecRates() {
        log.debug("REST request to get all HydroQuebecRates");
        return hydroQuebecRateRepository.findAll();
    }

    /**
     * {@code GET  /hydro-quebec-rates/:id} : get the "id" hydroQuebecRate.
     *
     * @param id the id of the hydroQuebecRate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hydroQuebecRate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HydroQuebecRate> getHydroQuebecRate(@PathVariable("id") Long id) {
        log.debug("REST request to get HydroQuebecRate : {}", id);
        Optional<HydroQuebecRate> hydroQuebecRate = hydroQuebecRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hydroQuebecRate);
    }

    /**
     * {@code DELETE  /hydro-quebec-rates/:id} : delete the "id" hydroQuebecRate.
     *
     * @param id the id of the hydroQuebecRate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHydroQuebecRate(@PathVariable("id") Long id) {
        log.debug("REST request to delete HydroQuebecRate : {}", id);
        hydroQuebecRateRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
