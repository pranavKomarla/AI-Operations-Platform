package com.pranav.hiring.controller;

import com.pranav.hiring.dto.JobApplicationDetails;
import com.pranav.hiring.dto.JobApplicationSubmissionRequest;
import com.pranav.hiring.dto.CandidateApplication;
import com.pranav.hiring.service.JobApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private static final Logger log = LoggerFactory.getLogger(JobApplicationController.class);

    @PostMapping
    public ResponseEntity<Void> submitApplication(@RequestBody JobApplicationSubmissionRequest request){
        log.info("Submitting job application. jobId: {}, candidateId: {}", request.jobId(), request.candidateId());
        return ResponseEntity.accepted().build();
    }

    @GetMapping(params = "candidateId")
    public List<CandidateApplication> getApplicationsByCandidateId(Integer candidateId){
        log.info("Fetching job applications by candidateId: {}", candidateId);
        return Collections.emptyList();
    }

    @GetMapping(params = "jobId")
    public List<JobApplicationDetails> getApplicationsByJobId(Integer jobId){
        log.info("Fetching job applications by jobId: {}", jobId);
        return Collections.emptyList();
    }

}