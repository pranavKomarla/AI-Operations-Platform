package com.pranav.hiring.dto;

public record JobApplicationSubmissionRequest(Integer jobId,
                                              Integer candidateId,
                                              String resume) {
}
