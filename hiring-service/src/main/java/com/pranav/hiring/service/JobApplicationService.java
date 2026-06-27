package com.pranav.hiring.service;


import com.pranav.hiring.dto.JobApplicationSubmissionRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {


    @Transactional
    public void submitApplication(JobApplicationSubmissionRequest request) {

    }

}
