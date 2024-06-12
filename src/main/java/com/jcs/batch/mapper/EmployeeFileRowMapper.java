package com.jcs.batch.mapper;

import com.jcs.batch.model.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

@Slf4j
public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDTO> {
    @Override
    public EmployeeDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(fieldSet.readString("EmployeeId"));
        employeeDTO.setFirstName(fieldSet.readString("EmpFirstName"));
        employeeDTO.setLastName(fieldSet.readString("EmpLastName"));
        employeeDTO.setEmail(fieldSet.readString("EmpEmail"));
        employeeDTO.setPhoneNo(fieldSet.readString("EmpPhoneNo"));
        employeeDTO.setHireDate(fieldSet.readString("EmpHireDate"));
        employeeDTO.setJobId(fieldSet.readString("EmpJobId"));
        employeeDTO.setSalary(fieldSet.readString("EmpSalary"));
        log.info("Mapping Emp obj:: {}", employeeDTO);
        return employeeDTO;
    }
}
