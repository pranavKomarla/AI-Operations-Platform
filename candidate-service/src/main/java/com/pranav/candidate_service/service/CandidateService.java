package com.pranav.candidate_service.service;

import org.springframework.stereotype.Service;

import com.pranav.candidate_service.repository.CandidateRepository;
import com.pranav.candidate_service.dto.CandidateDetails;
import com.pranav.candidate_service.mapper.EntityDtoMapper;
import java.util.Objects;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public CandidateDetails getCandidateDetails(Integer id) {
        return this.candidateRepository.findById(Objects.requireNonNull(id))
                                       .map(EntityDtoMapper::toCandidateDetails)
                                       .orElseThrow();
    }

}
