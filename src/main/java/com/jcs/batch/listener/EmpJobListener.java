package com.jcs.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.LocalDateTime;

@Slf4j
public class EmpJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("job started at:{}", LocalDateTime.now());
        log.info("job status while starting:{}", jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("job ended at:{}", LocalDateTime.now());
        log.info("job status when ended:{}", jobExecution.getStatus());
    }
}
