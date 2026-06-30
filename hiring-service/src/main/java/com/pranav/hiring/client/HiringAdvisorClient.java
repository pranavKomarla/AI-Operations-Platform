package com.pranav.hiring.client;

import com.pranav.hiring.dto.JobApplicationEvaluationRequest;
import com.pranav.hiring.dto.JobApplicationEvaluationResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import java.util.Objects;

import java.util.Map;

public class HiringAdvisorClient {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public HiringAdvisorClient(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }

    public JobApplicationEvaluationResponse evaluate(JobApplicationEvaluationRequest request) {
        var params = Objects.requireNonNull(this.toPromptParams(request));
        return this.chatClient.prompt()
                              .user(spec -> spec.params(params))
                              .call()
                              .entity(JobApplicationEvaluationResponse.class);
    }

    private Map<String, Object> toPromptParams(JobApplicationEvaluationRequest request) {
        return Objects.requireNonNull(this.objectMapper.convertValue(request, new TypeReference<Map<String, Object>>() {
        }));
    }

}