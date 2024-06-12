package com.jcs.batch.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Slf4j
public class EmployeeJobSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
        log.info("shouldSkip-->skipCount::{}", skipCount);
        if(skipCount == 3){
            return false;
        }
        return true;
    }
}
