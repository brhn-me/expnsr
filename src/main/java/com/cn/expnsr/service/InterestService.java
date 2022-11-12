package com.cn.expnsr.service;

import com.cn.expnsr.domain.Interest;
import com.cn.expnsr.repository.InterestRepository;
import com.cn.expnsr.service.dto.InterestDTO;
import com.cn.expnsr.service.mapper.InterestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Interest}.
 */
@Service
@Transactional
public class InterestService {

    private final Logger log = LoggerFactory.getLogger(InterestService.class);

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    public InterestService(InterestRepository interestRepository, InterestMapper interestMapper) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
    }

    /**
     * Save a interest.
     *
     * @param interestDTO the entity to save.
     * @return the persisted entity.
     */
    public InterestDTO save(InterestDTO interestDTO) {
        log.debug("Request to save Interest : {}", interestDTO);
        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        return interestMapper.toDto(interest);
    }

    /**
     * Update a interest.
     *
     * @param interestDTO the entity to save.
     * @return the persisted entity.
     */
    public InterestDTO update(InterestDTO interestDTO) {
        log.debug("Request to update Interest : {}", interestDTO);
        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        return interestMapper.toDto(interest);
    }

    /**
     * Partially update a interest.
     *
     * @param interestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterestDTO> partialUpdate(InterestDTO interestDTO) {
        log.debug("Request to partially update Interest : {}", interestDTO);

        return interestRepository
            .findById(interestDTO.getId())
            .map(existingInterest -> {
                interestMapper.partialUpdate(existingInterest, interestDTO);

                return existingInterest;
            })
            .map(interestRepository::save)
            .map(interestMapper::toDto);
    }

    /**
     * Get all the interests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InterestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interests");
        return interestRepository.findAll(pageable).map(interestMapper::toDto);
    }

    /**
     * Get one interest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InterestDTO> findOne(Long id) {
        log.debug("Request to get Interest : {}", id);
        return interestRepository.findById(id).map(interestMapper::toDto);
    }

    /**
     * Delete the interest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Interest : {}", id);
        interestRepository.deleteById(id);
    }
}
