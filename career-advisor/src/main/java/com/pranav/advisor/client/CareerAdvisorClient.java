package com.pranav.advisor.client;

import com.pranav.advisor.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Objects;

public class CareerAdvisorClient {

    private static final String JOB = "job";
    private static final String JOBS = "jobs";
    private static final String CANDIDATE = "candidate";

    private final ChatClient chatClient;
    private final CareerAdvisorPrompts prompts;
    private final ObjectMapper objectMapper;

    public CareerAdvisorClient(ChatClient chatClient, CareerAdvisorPrompts prompts, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.prompts = prompts;
        this.objectMapper = objectMapper;
    }

    public List<JobEvaluationResult> evaluateJobs(CandidateDetails candidate, List<JobSummary> jobs) {
        var response = this.chatClient.prompt()
                                      .system(Objects.requireNonNull(this.prompts.evaluateJobs().system()))
                                      .user(spec -> spec.text(Objects.requireNonNull(this.prompts.evaluateJobs().user()))
                                                        .param(CANDIDATE, Objects.requireNonNull(this.toJsonString(candidate)))
                                                        .param(JOBS, Objects.requireNonNull(this.toJsonString(jobs))))
                                      .call()
                                      .content();
        return this.parseJobEvaluationResults(Objects.requireNonNull(response));
    }

    public JobsComparisonResult compareJobs(CandidateDetails candidate, List<JobDetails> jobs) {
        return this.chatClient.prompt()
                              .system(Objects.requireNonNull(this.prompts.compareJobs().system()))
                              .user(spec -> spec.text(Objects.requireNonNull(this.prompts.compareJobs().user()))
                                                .param(CANDIDATE, Objects.requireNonNull(this.toJsonString(candidate)))
                                                .param(JOBS, Objects.requireNonNull(this.toJsonString(jobs))))
                              .call()
                              .entity(JobsComparisonResult.class);
    }

    public TailoredResume generateResume(CandidateDetails candidate, JobDetails job) {
        var resume = this.chatClient.prompt()
                              .system(Objects.requireNonNull(this.prompts.generateResume().system()))
                              .user(spec -> spec.text(Objects.requireNonNull(this.prompts.generateResume().user()))
                                                .param(CANDIDATE, Objects.requireNonNull(this.toJsonString(candidate)))
                                                .param(JOB, Objects.requireNonNull(this.toJsonString(job))))
                              .call()
                              .content();
        return new TailoredResume(job.id(), candidate.id(), Objects.requireNonNull(resume));
    }

    // Providing data as structured JSON ensures the model correctly maps attributes between the candidate and job requirements.
    private String toJsonString(Object object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request payload", e);
        }
    }

    private List<JobEvaluationResult> parseJobEvaluationResults(String response) {
        var normalized = response.trim();

        if (normalized.startsWith("```") && normalized.endsWith("```")) {
            normalized = normalized.replaceFirst("^```[a-zA-Z]*\\s*", "");
            normalized = normalized.replaceFirst("\\s*```$", "");
            normalized = normalized.trim();
        }

        // Some model responses return comma-separated objects instead of a JSON array.
        if (!normalized.startsWith("[") && normalized.startsWith("{")) {
            normalized = "[" + normalized + "]";
        }

        try {
            return this.objectMapper.readValue(normalized, new TypeReference<List<JobEvaluationResult>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse job evaluation response as JSON array", e);
        }
    }

}