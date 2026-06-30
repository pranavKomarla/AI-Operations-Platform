package com.pranav.job_service.service;
import com.pranav.job_service.dto.JobDetails;
import com.pranav.job_service.dto.JobSummary;
import com.pranav.job_service.repository.JobRepository;
import com.pranav.job_service.mapper.EntityDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobDetails getJobById(Integer id) {
        return this.jobRepository.findById(Objects.requireNonNull(id))
                                 .map(EntityDtoMapper::toJobDetails)
                                 .orElseThrow();
    }

    public List<JobDetails> getJobsByIds(List<Integer> ids) {
        return this.jobRepository.findAllById(Objects.requireNonNull(ids))
                                 .stream()
                                 .filter(Objects::nonNull)
                                 .map(EntityDtoMapper::toJobDetails)
                                 .toList();
    }

    public List<JobSummary> searchBySkills(List<String> skills) {
        var lowercasedSkills = skills.stream()
                                     .map(s -> Objects.requireNonNull(s).toLowerCase())
                                     .toList();
        return this.jobRepository.findByAnySkill(lowercasedSkills)
                                 .stream()
                                 .filter(Objects::nonNull)
                                 .map(EntityDtoMapper::toJobSummary)
                                 .toList();
    }

}