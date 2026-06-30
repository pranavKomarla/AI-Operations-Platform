package com.pranav.advisor.config;
import com.pranav.advisor.client.CandidateClient;
import com.pranav.advisor.client.CareerAdvisorClient;
import com.pranav.advisor.client.JobClient;
import com.pranav.advisor.dto.CareerAdvisorPrompts;
import com.pranav.advisor.dto.PromptSet;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

@Configuration
public class ApplicationConfiguration {

    private static final String USER_TEMPLATE_PATH_FORMAT = "classpath:prompt-templates/%s/user.txt";
    private static final String SYSTEM_TEMPLATE_PATH_FORMAT = "classpath:prompt-templates/%s/system.txt";

    private final ResourceLoader resourceLoader;

    public ApplicationConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public JobClient jobClient(RestClient.Builder builder, @Value("${job-service.url}") String baseUrl){
        var restClient = builder.baseUrl(Objects.requireNonNull(baseUrl)).build();
        return new JobClient(restClient);
    }

    @Bean
    public CandidateClient candidateClient(RestClient.Builder builder, @Value("${candidate-service.url}") String baseUrl){
        var restClient = builder.baseUrl(Objects.requireNonNull(baseUrl)).build();
        return new CandidateClient(restClient);
    }

    @Bean
    public CareerAdvisorClient careerAdvisorClient(ChatClient.Builder builder, ObjectMapper objectMapper){
        var chatClient = builder.build();
        var prompts = new CareerAdvisorPrompts(
                this.getPromptSet("compare-jobs"),
                this.getPromptSet("evaluate-jobs"),
                this.getPromptSet("generate-resume")
        );
        return new CareerAdvisorClient(chatClient, prompts, objectMapper);
    }

    private PromptSet getPromptSet(String feature){
        return new PromptSet(
                this.getResourceContent(SYSTEM_TEMPLATE_PATH_FORMAT.formatted(feature)),
                this.getResourceContent(USER_TEMPLATE_PATH_FORMAT.formatted(feature))
        );
    }

    private String getResourceContent(String resourcePath){
        try{
            var resource = this.resourceLoader.getResource(Objects.requireNonNull(resourcePath));
            return resource.getContentAsString(Objects.requireNonNull(Charset.defaultCharset()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}