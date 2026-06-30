package com.pranav.hiring.service;

import com.pranav.hiring.client.JobClient;
import com.pranav.hiring.dto.CandidateApplication;
import com.pranav.hiring.dto.JobApplicationDetails;
import com.pranav.hiring.dto.JobApplicationSubmissionRequest;
import com.pranav.hiring.dto.JobApplicationSubmittedEvent;
import com.pranav.hiring.mapper.EntityDtoMapper;
import com.pranav.hiring.repository.JobApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class JobApplicationService {

    private final JobClient jobClient;
    private final JobApplicationRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public JobApplicationService(JobClient jobClient, JobApplicationRepository repository, ApplicationEventPublisher eventPublisher) {
        this.jobClient = jobClient;
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void submitApplication(JobApplicationSubmissionRequest request) {
        var jobApplication = this.repository.findByJobIdAndCandidateId(request.jobId(), request.candidateId())
                                            .orElseGet(() -> EntityDtoMapper.toJobApplication(request));
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setResume(request.resume());
        jobApplication.setMatchScore(null);
        jobApplication.setMatchReasoning(null);
        this.repository.save(jobApplication);
        this.eventPublisher.publishEvent(new JobApplicationSubmittedEvent(jobApplication.getId()));
    }

    public List<JobApplicationDetails> getApplicationsByJobId(Integer jobId) {
        return this.repository.findByJobId(jobId)
                              .stream()
                              .map(EntityDtoMapper::toJobApplicationDetails)
                              .toList();
    }

    public List<CandidateApplication> getApplicationsByCandidateId(Integer candidateId) {
        var jobApplications = this.repository.findByCandidateId(candidateId);
        if (jobApplications.isEmpty()) {
            return Collections.emptyList();
        }
        var jobIds = jobApplications.stream()
                        .map(jobApplication -> Objects.requireNonNull(jobApplication).getJobId())
                                    .toList();
        var jobsMap = this.jobClient.getJobsDetails(jobIds)
                                    .stream()
                                    .collect(Collectors.toMap(
                            jobDetails -> Objects.requireNonNull(jobDetails).id(),
                                            Function.identity()
                                    ));
        return jobApplications.stream()
                              .map(jobApplication -> EntityDtoMapper.toCandidateJobApplication(jobApplication, jobsMap.get(jobApplication.getJobId())))
                              .toList();
    }

}