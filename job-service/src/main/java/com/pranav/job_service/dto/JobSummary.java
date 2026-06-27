package com.pranav.job_service.dto;

import java.util.List;

public record JobSummary(Integer id,
                         String title,
                         String location,
                         String employer,
                         List<String> requiredSkills) {
}