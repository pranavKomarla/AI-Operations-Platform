package com.pranav.advisor.client;

import com.pranav.advisor.dto.JobDetails;
import com.pranav.advisor.dto.JobSummary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class JobClient {

    private static final String JOB_BY_ID_URI = "/api/jobs/{id}";
    private static final String JOBS_URI = "/api/jobs";
    private final RestClient restClient;

    public JobClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public JobDetails getJobDetails(Integer id) {
        return this.restClient.get()
                              .uri(JOB_BY_ID_URI, id)
                              .retrieve()
                              .body(JobDetails.class);
    }

    public List<JobDetails> getJobsDetails(List<Integer> ids) {
        return this.restClient.get()
                              .uri(uriBuilder -> uriBuilder.path(JOBS_URI)
                                                           .queryParam("ids", ids.toArray())
                                                           .build())
                              .retrieve()
                              .body(new ParameterizedTypeReference<List<JobDetails>>() {
                              });
    }

    public List<JobSummary> searchBySkills(List<String> skills) {
        return this.restClient.get()
                              .uri(uriBuilder -> uriBuilder.path(JOBS_URI)
                                                           .queryParam("skills", skills.toArray())
                                                           .build())
                              .retrieve()
                              .body(new ParameterizedTypeReference<List<JobSummary>>() {
                              });
    }
}