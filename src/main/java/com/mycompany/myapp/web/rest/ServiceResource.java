package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.repository.ServiceRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Service}.
 */
@RestController
@RequestMapping("/api/services")
@Transactional
public class ServiceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceResource.class);

    private static final String ENTITY_NAME = "service";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRepository serviceRepository;

    public ServiceResource(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * {@code POST  /services} : Create a new service.
     *
     * @param service the service to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new service, or with status {@code 400 (Bad Request)} if the service has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Service> createService(@RequestBody Service service) throws URISyntaxException {
        log.debug("REST request to save Service : {}", service);
        if (service.getId() != null) {
            throw new BadRequestAlertException("A new service cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Service result = serviceRepository.save(service);
        return ResponseEntity
            .created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /services/:id} : Updates an existing service.
     *
     * @param id the id of the service to save.
     * @param service the service to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated service,
     * or with status {@code 400 (Bad Request)} if the service is not valid,
     * or with status {@code 500 (Internal Server Error)} if the service couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable(value = "id", required = false) final Long id, @RequestBody Service service)
        throws URISyntaxException {
        log.debug("REST request to update Service : {}, {}", id, service);
        if (service.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, service.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Service result = serviceRepository.save(service);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, service.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /services/:id} : Partial updates given fields of an existing service, field will ignore if it is null
     *
     * @param id the id of the service to save.
     * @param service the service to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated service,
     * or with status {@code 400 (Bad Request)} if the service is not valid,
     * or with status {@code 404 (Not Found)} if the service is not found,
     * or with status {@code 500 (Internal Server Error)} if the service couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Service> partialUpdateService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Service service
    ) throws URISyntaxException {
        log.debug("REST request to partial update Service partially : {}, {}", id, service);
        if (service.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, service.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Service> result = serviceRepository
            .findById(service.getId())
            .map(existingService -> {
                if (service.getNameService() != null) {
                    existingService.setNameService(service.getNameService());
                }
                if (service.getCompanyName() != null) {
                    existingService.setCompanyName(service.getCompanyName());
                }
                if (service.getPhone() != null) {
                    existingService.setPhone(service.getPhone());
                }
                if (service.getEmail() != null) {
                    existingService.setEmail(service.getEmail());
                }
                if (service.getPlansAllFloorsVentilationPlans() != null) {
                    existingService.setPlansAllFloorsVentilationPlans(service.getPlansAllFloorsVentilationPlans());
                }
                if (service.getEnergySimulationReport() != null) {
                    existingService.setEnergySimulationReport(service.getEnergySimulationReport());
                }
                if (service.getWindowsTechnicalSheetAndUFactor() != null) {
                    existingService.setWindowsTechnicalSheetAndUFactor(service.getWindowsTechnicalSheetAndUFactor());
                }
                if (service.getCompleteWallSection() != null) {
                    existingService.setCompleteWallSection(service.getCompleteWallSection());
                }
                if (service.getBrandModelVentilationDevices() != null) {
                    existingService.setBrandModelVentilationDevices(service.getBrandModelVentilationDevices());
                }
                if (service.getBrandModelVeaters() != null) {
                    existingService.setBrandModelVeaters(service.getBrandModelVeaters());
                }
                if (service.getBrandModelHotWaterTanks() != null) {
                    existingService.setBrandModelHotWaterTanks(service.getBrandModelHotWaterTanks());
                }
                if (service.getBrandModelHeatPumpAirConditioningUnits() != null) {
                    existingService.setBrandModelHeatPumpAirConditioningUnits(service.getBrandModelHeatPumpAirConditioningUnits());
                }
                if (service.getTypeLighting() != null) {
                    existingService.setTypeLighting(service.getTypeLighting());
                }
                if (service.getQuantityEachModelAndDLCProductID() != null) {
                    existingService.setQuantityEachModelAndDLCProductID(service.getQuantityEachModelAndDLCProductID());
                }
                if (service.getChecksProvideTechnicalDataSheets() != null) {
                    existingService.setChecksProvideTechnicalDataSheets(service.getChecksProvideTechnicalDataSheets());
                }
                if (service.getBrandModelPlumbingFixtures() != null) {
                    existingService.setBrandModelPlumbingFixtures(service.getBrandModelPlumbingFixtures());
                }
                if (service.getOtherRelevantDevicesCertification() != null) {
                    existingService.setOtherRelevantDevicesCertification(service.getOtherRelevantDevicesCertification());
                }
                if (service.getHeatRecoveryGrayWaterSolarOther() != null) {
                    existingService.setHeatRecoveryGrayWaterSolarOther(service.getHeatRecoveryGrayWaterSolarOther());
                }

                return existingService;
            })
            .map(serviceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, service.getId().toString())
        );
    }

    /**
     * {@code GET  /services} : get all the services.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @GetMapping("")
    public List<Service> getAllServices(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("invoice-is-null".equals(filter)) {
            log.debug("REST request to get all Services where invoice is null");
            return StreamSupport
                .stream(serviceRepository.findAll().spliterator(), false)
                .filter(service -> service.getInvoice() == null)
                .toList();
        }
        log.debug("REST request to get all Services");
        if (eagerload) {
            return serviceRepository.findAllWithEagerRelationships();
        } else {
            return serviceRepository.findAll();
        }
    }

    /**
     * {@code GET  /services/:id} : get the "id" service.
     *
     * @param id the id of the service to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the service, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Service> getService(@PathVariable("id") Long id) {
        log.debug("REST request to get Service : {}", id);
        Optional<Service> service = serviceRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(service);
    }

    /**
     * {@code DELETE  /services/:id} : delete the "id" service.
     *
     * @param id the id of the service to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") Long id) {
        log.debug("REST request to delete Service : {}", id);
        serviceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
