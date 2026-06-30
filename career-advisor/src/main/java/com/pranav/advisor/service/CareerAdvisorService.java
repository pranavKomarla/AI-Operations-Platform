package com.pranav.advisor.service;

import com.pranav.advisor.client.CareerAdvisorClient;
import com.pranav.advisor.client.CandidateClient;
import com.pranav.advisor.client.JobClient;
import com.pranav.advisor.dto.JobEvaluationResult;
import com.pranav.advisor.dto.JobsComparisonResult;
import com.pranav.advisor.dto.TailoredResume;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CareerAdvisorService {

    private final JobClient jobClient;
    private final CandidateClient candidateClient;
    private final CareerAdvisorClient advisorClient;

    public CareerAdvisorService(JobClient jobClient, CandidateClient candidateClient, CareerAdvisorClient advisorClient) {
        this.jobClient = jobClient;
        this.candidateClient = candidateClient;
        this.advisorClient = advisorClient;
    }

    public List<JobEvaluationResult> findJobs(Integer candidateId) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var jobs = this.jobClient.searchBySkills(candidate.skills());
        return this.advisorClient.evaluateJobs(candidate, jobs)
                                 .stream()
                                 .filter(Objects::nonNull)
                                 .sorted((r1, r2) -> Integer.compare(Objects.requireNonNull(r2).matchScore(), Objects.requireNonNull(r1).matchScore()))
                                 .toList();
    }

    public JobsComparisonResult compareJobs(Integer candidateId, List<Integer> jobIds) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var jobs = this.jobClient.getJobsDetails(jobIds);
        return this.advisorClient.compareJobs(candidate, jobs);
    }

    public TailoredResume generateResume(Integer candidateId, Integer jobId) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var job = this.jobClient.getJobDetails(jobId);
        return this.advisorClient.generateResume(candidate, job);
    }

}