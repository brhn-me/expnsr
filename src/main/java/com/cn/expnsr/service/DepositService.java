package com.cn.expnsr.service;

import com.cn.expnsr.domain.Deposit;
import com.cn.expnsr.repository.DepositRepository;
import com.cn.expnsr.service.dto.DepositDTO;
import com.cn.expnsr.service.mapper.DepositMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deposit}.
 */
@Service
@Transactional
public class DepositService {

    private final Logger log = LoggerFactory.getLogger(DepositService.class);

    private final DepositRepository depositRepository;

    private final DepositMapper depositMapper;

    public DepositService(DepositRepository depositRepository, DepositMapper depositMapper) {
        this.depositRepository = depositRepository;
        this.depositMapper = depositMapper;
    }

    /**
     * Save a deposit.
     *
     * @param depositDTO the entity to save.
     * @return the persisted entity.
     */
    public DepositDTO save(DepositDTO depositDTO) {
        log.debug("Request to save Deposit : {}", depositDTO);
        Deposit deposit = depositMapper.toEntity(depositDTO);
        deposit = depositRepository.save(deposit);
        return depositMapper.toDto(deposit);
    }

    /**
     * Update a deposit.
     *
     * @param depositDTO the entity to save.
     * @return the persisted entity.
     */
    public DepositDTO update(DepositDTO depositDTO) {
        log.debug("Request to update Deposit : {}", depositDTO);
        Deposit deposit = depositMapper.toEntity(depositDTO);
        deposit = depositRepository.save(deposit);
        return depositMapper.toDto(deposit);
    }

    /**
     * Partially update a deposit.
     *
     * @param depositDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepositDTO> partialUpdate(DepositDTO depositDTO) {
        log.debug("Request to partially update Deposit : {}", depositDTO);

        return depositRepository
            .findById(depositDTO.getId())
            .map(existingDeposit -> {
                depositMapper.partialUpdate(existingDeposit, depositDTO);

                return existingDeposit;
            })
            .map(depositRepository::save)
            .map(depositMapper::toDto);
    }

    /**
     * Get all the deposits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepositDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deposits");
        return depositRepository.findAll(pageable).map(depositMapper::toDto);
    }

    /**
     * Get one deposit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepositDTO> findOne(Long id) {
        log.debug("Request to get Deposit : {}", id);
        return depositRepository.findById(id).map(depositMapper::toDto);
    }

    /**
     * Delete the deposit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deposit : {}", id);
        depositRepository.deleteById(id);
    }
}
