package com.pranav.hiring.service;

import com.pranav.hiring.client.HiringAdvisorClient;
import com.pranav.hiring.client.JobClient;
import com.pranav.hiring.dto.JobApplicationSubmittedEvent;
import com.pranav.hiring.mapper.EntityDtoMapper;
import com.pranav.hiring.repository.JobApplicationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

@Service
public class JobApplicationEvaluationListener {

    private final JobClient jobClient;
    private final HiringAdvisorClient advisorClient;
    private final JobApplicationRepository repository;

    public JobApplicationEvaluationListener(JobClient jobClient, HiringAdvisorClient advisorClient, JobApplicationRepository repository) {
        this.jobClient = jobClient;
        this.advisorClient = advisorClient;
        this.repository = repository;
    }

    @Async
    @TransactionalEventListener
    public void handle(JobApplicationSubmittedEvent event){
        var jobApplication = this.repository.findById(Objects.requireNonNull(event.applicationId())).orElseThrow();
        var jobDetails = this.jobClient.getJobDetails(jobApplication.getJobId());
        var evaluationRequest = EntityDtoMapper.toJobApplicationEvaluationRequest(jobApplication, jobDetails);
        var evaluationResponse = this.advisorClient.evaluate(evaluationRequest);
        jobApplication.setMatchScore(evaluationResponse.matchScore());
        jobApplication.setMatchReasoning(evaluationResponse.matchReasoning());
        this.repository.save(jobApplication);
    }

}