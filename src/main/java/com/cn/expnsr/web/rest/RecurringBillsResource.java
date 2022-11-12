package com.cn.expnsr.web.rest;

import com.cn.expnsr.repository.RecurringBillsRepository;
import com.cn.expnsr.service.RecurringBillsService;
import com.cn.expnsr.service.dto.RecurringBillsDTO;
import com.cn.expnsr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cn.expnsr.domain.RecurringBills}.
 */
@RestController
@RequestMapping("/api")
public class RecurringBillsResource {

    private final Logger log = LoggerFactory.getLogger(RecurringBillsResource.class);

    private static final String ENTITY_NAME = "recurringBills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecurringBillsService recurringBillsService;

    private final RecurringBillsRepository recurringBillsRepository;

    public RecurringBillsResource(RecurringBillsService recurringBillsService, RecurringBillsRepository recurringBillsRepository) {
        this.recurringBillsService = recurringBillsService;
        this.recurringBillsRepository = recurringBillsRepository;
    }

    /**
     * {@code POST  /recurring-bills} : Create a new recurringBills.
     *
     * @param recurringBillsDTO the recurringBillsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recurringBillsDTO, or with status {@code 400 (Bad Request)} if the recurringBills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurring-bills")
    public ResponseEntity<RecurringBillsDTO> createRecurringBills(@Valid @RequestBody RecurringBillsDTO recurringBillsDTO)
        throws URISyntaxException {
        log.debug("REST request to save RecurringBills : {}", recurringBillsDTO);
        if (recurringBillsDTO.getId() != null) {
            throw new BadRequestAlertException("A new recurringBills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecurringBillsDTO result = recurringBillsService.save(recurringBillsDTO);
        return ResponseEntity
            .created(new URI("/api/recurring-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurring-bills/:id} : Updates an existing recurringBills.
     *
     * @param id the id of the recurringBillsDTO to save.
     * @param recurringBillsDTO the recurringBillsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurringBillsDTO,
     * or with status {@code 400 (Bad Request)} if the recurringBillsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recurringBillsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurring-bills/{id}")
    public ResponseEntity<RecurringBillsDTO> updateRecurringBills(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecurringBillsDTO recurringBillsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RecurringBills : {}, {}", id, recurringBillsDTO);
        if (recurringBillsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurringBillsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurringBillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecurringBillsDTO result = recurringBillsService.update(recurringBillsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recurringBillsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recurring-bills/:id} : Partial updates given fields of an existing recurringBills, field will ignore if it is null
     *
     * @param id the id of the recurringBillsDTO to save.
     * @param recurringBillsDTO the recurringBillsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurringBillsDTO,
     * or with status {@code 400 (Bad Request)} if the recurringBillsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recurringBillsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recurringBillsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recurring-bills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecurringBillsDTO> partialUpdateRecurringBills(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecurringBillsDTO recurringBillsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecurringBills partially : {}, {}", id, recurringBillsDTO);
        if (recurringBillsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurringBillsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurringBillsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecurringBillsDTO> result = recurringBillsService.partialUpdate(recurringBillsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recurringBillsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recurring-bills} : get all the recurringBills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurringBills in body.
     */
    @GetMapping("/recurring-bills")
    public List<RecurringBillsDTO> getAllRecurringBills() {
        log.debug("REST request to get all RecurringBills");
        return recurringBillsService.findAll();
    }

    /**
     * {@code GET  /recurring-bills/:id} : get the "id" recurringBills.
     *
     * @param id the id of the recurringBillsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recurringBillsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurring-bills/{id}")
    public ResponseEntity<RecurringBillsDTO> getRecurringBills(@PathVariable Long id) {
        log.debug("REST request to get RecurringBills : {}", id);
        Optional<RecurringBillsDTO> recurringBillsDTO = recurringBillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recurringBillsDTO);
    }

    /**
     * {@code DELETE  /recurring-bills/:id} : delete the "id" recurringBills.
     *
     * @param id the id of the recurringBillsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurring-bills/{id}")
    public ResponseEntity<Void> deleteRecurringBills(@PathVariable Long id) {
        log.debug("REST request to delete RecurringBills : {}", id);
        recurringBillsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
