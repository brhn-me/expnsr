package com.cn.expnsr.web.rest;

import com.cn.expnsr.repository.DepositRepository;
import com.cn.expnsr.service.DepositService;
import com.cn.expnsr.service.dto.DepositDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cn.expnsr.domain.Deposit}.
 */
@RestController
@RequestMapping("/api")
public class DepositResource {

    private final Logger log = LoggerFactory.getLogger(DepositResource.class);

    private static final String ENTITY_NAME = "deposit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepositService depositService;

    private final DepositRepository depositRepository;

    public DepositResource(DepositService depositService, DepositRepository depositRepository) {
        this.depositService = depositService;
        this.depositRepository = depositRepository;
    }

    /**
     * {@code POST  /deposits} : Create a new deposit.
     *
     * @param depositDTO the depositDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depositDTO, or with status {@code 400 (Bad Request)} if the deposit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deposits")
    public ResponseEntity<DepositDTO> createDeposit(@Valid @RequestBody DepositDTO depositDTO) throws URISyntaxException {
        log.debug("REST request to save Deposit : {}", depositDTO);
        if (depositDTO.getId() != null) {
            throw new BadRequestAlertException("A new deposit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositDTO result = depositService.save(depositDTO);
        return ResponseEntity
            .created(new URI("/api/deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deposits/:id} : Updates an existing deposit.
     *
     * @param id the id of the depositDTO to save.
     * @param depositDTO the depositDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositDTO,
     * or with status {@code 400 (Bad Request)} if the depositDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depositDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deposits/{id}")
    public ResponseEntity<DepositDTO> updateDeposit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepositDTO depositDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Deposit : {}, {}", id, depositDTO);
        if (depositDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depositDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DepositDTO result = depositService.update(depositDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depositDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deposits/:id} : Partial updates given fields of an existing deposit, field will ignore if it is null
     *
     * @param id the id of the depositDTO to save.
     * @param depositDTO the depositDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositDTO,
     * or with status {@code 400 (Bad Request)} if the depositDTO is not valid,
     * or with status {@code 404 (Not Found)} if the depositDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the depositDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deposits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepositDTO> partialUpdateDeposit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepositDTO depositDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deposit partially : {}, {}", id, depositDTO);
        if (depositDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depositDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepositDTO> result = depositService.partialUpdate(depositDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depositDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /deposits} : get all the deposits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deposits in body.
     */
    @GetMapping("/deposits")
    public ResponseEntity<List<DepositDTO>> getAllDeposits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Deposits");
        Page<DepositDTO> page = depositService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deposits/:id} : get the "id" deposit.
     *
     * @param id the id of the depositDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depositDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deposits/{id}")
    public ResponseEntity<DepositDTO> getDeposit(@PathVariable Long id) {
        log.debug("REST request to get Deposit : {}", id);
        Optional<DepositDTO> depositDTO = depositService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositDTO);
    }

    /**
     * {@code DELETE  /deposits/:id} : delete the "id" deposit.
     *
     * @param id the id of the depositDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deposits/{id}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long id) {
        log.debug("REST request to delete Deposit : {}", id);
        depositService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
