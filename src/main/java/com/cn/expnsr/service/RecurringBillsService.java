package com.cn.expnsr.service;

import com.cn.expnsr.domain.RecurringBills;
import com.cn.expnsr.repository.RecurringBillsRepository;
import com.cn.expnsr.service.dto.RecurringBillsDTO;
import com.cn.expnsr.service.mapper.RecurringBillsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecurringBills}.
 */
@Service
@Transactional
public class RecurringBillsService {

    private final Logger log = LoggerFactory.getLogger(RecurringBillsService.class);

    private final RecurringBillsRepository recurringBillsRepository;

    private final RecurringBillsMapper recurringBillsMapper;

    public RecurringBillsService(RecurringBillsRepository recurringBillsRepository, RecurringBillsMapper recurringBillsMapper) {
        this.recurringBillsRepository = recurringBillsRepository;
        this.recurringBillsMapper = recurringBillsMapper;
    }

    /**
     * Save a recurringBills.
     *
     * @param recurringBillsDTO the entity to save.
     * @return the persisted entity.
     */
    public RecurringBillsDTO save(RecurringBillsDTO recurringBillsDTO) {
        log.debug("Request to save RecurringBills : {}", recurringBillsDTO);
        RecurringBills recurringBills = recurringBillsMapper.toEntity(recurringBillsDTO);
        recurringBills = recurringBillsRepository.save(recurringBills);
        return recurringBillsMapper.toDto(recurringBills);
    }

    /**
     * Update a recurringBills.
     *
     * @param recurringBillsDTO the entity to save.
     * @return the persisted entity.
     */
    public RecurringBillsDTO update(RecurringBillsDTO recurringBillsDTO) {
        log.debug("Request to update RecurringBills : {}", recurringBillsDTO);
        RecurringBills recurringBills = recurringBillsMapper.toEntity(recurringBillsDTO);
        recurringBills = recurringBillsRepository.save(recurringBills);
        return recurringBillsMapper.toDto(recurringBills);
    }

    /**
     * Partially update a recurringBills.
     *
     * @param recurringBillsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecurringBillsDTO> partialUpdate(RecurringBillsDTO recurringBillsDTO) {
        log.debug("Request to partially update RecurringBills : {}", recurringBillsDTO);

        return recurringBillsRepository
            .findById(recurringBillsDTO.getId())
            .map(existingRecurringBills -> {
                recurringBillsMapper.partialUpdate(existingRecurringBills, recurringBillsDTO);

                return existingRecurringBills;
            })
            .map(recurringBillsRepository::save)
            .map(recurringBillsMapper::toDto);
    }

    /**
     * Get all the recurringBills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecurringBillsDTO> findAll() {
        log.debug("Request to get all RecurringBills");
        return recurringBillsRepository
            .findAll()
            .stream()
            .map(recurringBillsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recurringBills by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecurringBillsDTO> findOne(Long id) {
        log.debug("Request to get RecurringBills : {}", id);
        return recurringBillsRepository.findById(id).map(recurringBillsMapper::toDto);
    }

    /**
     * Delete the recurringBills by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecurringBills : {}", id);
        recurringBillsRepository.deleteById(id);
    }
}
