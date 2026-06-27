package com.pranav.hiring.service;

import com.pranav.hiring.dto.JobApplicationSubmittedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationEvaluationListener {

    @Async
    @EventListener
    public void handle(JobApplicationSubmittedEvent event){

    }

}
