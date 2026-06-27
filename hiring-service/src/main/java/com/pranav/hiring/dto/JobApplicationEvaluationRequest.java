package com.pranav.hiring.dto;

import java.util.List;

public record JobApplicationEvaluationRequest(String jobTitle,
                                              String jobDescription,
                                              List<String> requiredSkills,
                                              String candidateResume) {
}
