package com.jcs.batch.processor;

import com.jcs.batch.model.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, EmployeeDTO> {

    @Autowired
    private ExecutionContext executionContext;

    @Override
    public EmployeeDTO process(EmployeeDTO employeeDTO) throws Exception {
        log.info("Value in execution context: processor::{}",executionContext.getString("clientName"));
        log.info("Processing Employee with id {}", employeeDTO.getEmpId());
//        if(Integer.valueOf(employeeDTO.getSalary())>10000){
//            throw new IllegalArgumentException("salary>10000 not allowed");
//        }
        employeeDTO.setNewSalary(Double.parseDouble(employeeDTO.getSalary()) * 2);
        return employeeDTO;
    }
}
