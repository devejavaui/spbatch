package com.jcs.batch.listener;

import com.jcs.batch.model.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class EmpStepReadListener implements ItemReadListener<EmployeeDTO> {
    @Override
    public void beforeRead() {
        log.info("inside before Read....");
    }

    @Override
    public void afterRead(EmployeeDTO item) {
        log.info("inside after Read:{}", item);
    }

    @Override
    public void onReadError(Exception ex) {
        log.info("inside onReadError:{}", ex.getMessage());
    }
}
