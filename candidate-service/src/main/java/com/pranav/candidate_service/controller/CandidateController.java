package com.pranav.candidate_service.controller;

import com.pranav.candidate_service.dto.CandidateDetails;
import com.pranav.candidate_service.service.CandidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private static final Logger log = LoggerFactory.getLogger(CandidateController.class);
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{candidateId}")
    public CandidateDetails getCandidateById(@PathVariable Integer candidateId) {
        log.info("Fetching candidate by id: {}", candidateId);
        return this.candidateService.getCandidateDetails(candidateId);
    }

}