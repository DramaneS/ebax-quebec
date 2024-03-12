package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Custumer;
import com.mycompany.myapp.repository.CustumerRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Custumer}.
 */
@RestController
@RequestMapping("/api/custumers")
@Transactional
public class CustumerResource {

    private final Logger log = LoggerFactory.getLogger(CustumerResource.class);

    private static final String ENTITY_NAME = "custumer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustumerRepository custumerRepository;

    public CustumerResource(CustumerRepository custumerRepository) {
        this.custumerRepository = custumerRepository;
    }

    /**
     * {@code POST  /custumers} : Create a new custumer.
     *
     * @param custumer the custumer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custumer, or with status {@code 400 (Bad Request)} if the custumer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Custumer> createCustumer(@RequestBody Custumer custumer) throws URISyntaxException {
        log.debug("REST request to save Custumer : {}", custumer);
        if (custumer.getId() != null) {
            throw new BadRequestAlertException("A new custumer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Custumer result = custumerRepository.save(custumer);
        return ResponseEntity
            .created(new URI("/api/custumers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custumers/:id} : Updates an existing custumer.
     *
     * @param id the id of the custumer to save.
     * @param custumer the custumer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custumer,
     * or with status {@code 400 (Bad Request)} if the custumer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custumer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Custumer> updateCustumer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Custumer custumer
    ) throws URISyntaxException {
        log.debug("REST request to update Custumer : {}, {}", id, custumer);
        if (custumer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custumer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custumerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Custumer result = custumerRepository.save(custumer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custumer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custumers/:id} : Partial updates given fields of an existing custumer, field will ignore if it is null
     *
     * @param id the id of the custumer to save.
     * @param custumer the custumer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custumer,
     * or with status {@code 400 (Bad Request)} if the custumer is not valid,
     * or with status {@code 404 (Not Found)} if the custumer is not found,
     * or with status {@code 500 (Internal Server Error)} if the custumer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Custumer> partialUpdateCustumer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Custumer custumer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Custumer partially : {}, {}", id, custumer);
        if (custumer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custumer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custumerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Custumer> result = custumerRepository
            .findById(custumer.getId())
            .map(existingCustumer -> {
                if (custumer.getNameCustumer() != null) {
                    existingCustumer.setNameCustumer(custumer.getNameCustumer());
                }
                if (custumer.getAdress() != null) {
                    existingCustumer.setAdress(custumer.getAdress());
                }
                if (custumer.getPhone() != null) {
                    existingCustumer.setPhone(custumer.getPhone());
                }
                if (custumer.getEmail() != null) {
                    existingCustumer.setEmail(custumer.getEmail());
                }

                return existingCustumer;
            })
            .map(custumerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custumer.getId().toString())
        );
    }

    /**
     * {@code GET  /custumers} : get all the custumers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custumers in body.
     */
    @GetMapping("")
    public List<Custumer> getAllCustumers() {
        log.debug("REST request to get all Custumers");
        return custumerRepository.findAll();
    }

    /**
     * {@code GET  /custumers/:id} : get the "id" custumer.
     *
     * @param id the id of the custumer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custumer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Custumer> getCustumer(@PathVariable("id") Long id) {
        log.debug("REST request to get Custumer : {}", id);
        Optional<Custumer> custumer = custumerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(custumer);
    }

    /**
     * {@code DELETE  /custumers/:id} : delete the "id" custumer.
     *
     * @param id the id of the custumer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustumer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Custumer : {}", id);
        custumerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
