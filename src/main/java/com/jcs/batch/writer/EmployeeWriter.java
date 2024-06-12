package com.jcs.batch.writer;

import com.jcs.batch.model.EmployeeDTO;
import com.jcs.batch.repository.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmployeeWriter implements ItemWriter<EmployeeDTO> {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ExecutionContext executionContext;

    @Override
    public void write(List<? extends EmployeeDTO> items) throws Exception {
        log.info("Value in execution context in writer::{}",executionContext.getString("clientName"));
        List<? extends EmployeeDTO> savedEmpList = employeeRepo.saveAll(items);
        log.info("Total Employees saved:{}", savedEmpList.size());
    }
}
