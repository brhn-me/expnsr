package com.cn.expnsr.service;

import com.cn.expnsr.domain.FixedDeposit;
import com.cn.expnsr.repository.FixedDepositRepository;
import com.cn.expnsr.service.dto.FixedDepositDTO;
import com.cn.expnsr.service.mapper.FixedDepositMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FixedDeposit}.
 */
@Service
@Transactional
public class FixedDepositService {

    private final Logger log = LoggerFactory.getLogger(FixedDepositService.class);

    private final FixedDepositRepository fixedDepositRepository;

    private final FixedDepositMapper fixedDepositMapper;

    public FixedDepositService(FixedDepositRepository fixedDepositRepository, FixedDepositMapper fixedDepositMapper) {
        this.fixedDepositRepository = fixedDepositRepository;
        this.fixedDepositMapper = fixedDepositMapper;
    }

    /**
     * Save a fixedDeposit.
     *
     * @param fixedDepositDTO the entity to save.
     * @return the persisted entity.
     */
    public FixedDepositDTO save(FixedDepositDTO fixedDepositDTO) {
        log.debug("Request to save FixedDeposit : {}", fixedDepositDTO);
        FixedDeposit fixedDeposit = fixedDepositMapper.toEntity(fixedDepositDTO);
        fixedDeposit = fixedDepositRepository.save(fixedDeposit);
        return fixedDepositMapper.toDto(fixedDeposit);
    }

    /**
     * Update a fixedDeposit.
     *
     * @param fixedDepositDTO the entity to save.
     * @return the persisted entity.
     */
    public FixedDepositDTO update(FixedDepositDTO fixedDepositDTO) {
        log.debug("Request to update FixedDeposit : {}", fixedDepositDTO);
        FixedDeposit fixedDeposit = fixedDepositMapper.toEntity(fixedDepositDTO);
        fixedDeposit.setIsPersisted();
        fixedDeposit = fixedDepositRepository.save(fixedDeposit);
        return fixedDepositMapper.toDto(fixedDeposit);
    }

    /**
     * Partially update a fixedDeposit.
     *
     * @param fixedDepositDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FixedDepositDTO> partialUpdate(FixedDepositDTO fixedDepositDTO) {
        log.debug("Request to partially update FixedDeposit : {}", fixedDepositDTO);

        return fixedDepositRepository
            .findById(fixedDepositDTO.getId())
            .map(existingFixedDeposit -> {
                fixedDepositMapper.partialUpdate(existingFixedDeposit, fixedDepositDTO);

                return existingFixedDeposit;
            })
            .map(fixedDepositRepository::save)
            .map(fixedDepositMapper::toDto);
    }

    /**
     * Get all the fixedDeposits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedDepositDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FixedDeposits");
        return fixedDepositRepository.findAll(pageable).map(fixedDepositMapper::toDto);
    }

    /**
     * Get one fixedDeposit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FixedDepositDTO> findOne(String id) {
        log.debug("Request to get FixedDeposit : {}", id);
        return fixedDepositRepository.findById(id).map(fixedDepositMapper::toDto);
    }

    /**
     * Delete the fixedDeposit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete FixedDeposit : {}", id);
        fixedDepositRepository.deleteById(id);
    }
}
