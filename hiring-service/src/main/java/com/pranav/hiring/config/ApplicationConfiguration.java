package com.pranav.hiring.config;

import com.pranav.hiring.client.HiringAdvisorClient;
import com.pranav.hiring.client.JobClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestClient;
import java.util.Objects;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public JobClient jobClient(RestClient.Builder builder, @Value("${job-service.url}") String baseUrl) {
        var restClient = builder.baseUrl(Objects.requireNonNull(baseUrl)).build();
        return new JobClient(restClient);
    }

    @Bean
    public HiringAdvisorClient hiringAdvisorClient(ChatClient.Builder builder, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        var chatClient = builder.defaultSystem(resourceLoader.getResource("classpath:prompt-templates/system.txt"))
                                .defaultUser(resourceLoader.getResource("classpath:prompt-templates/user.txt"))
                                .build();
        return new HiringAdvisorClient(chatClient, objectMapper);
    }

}