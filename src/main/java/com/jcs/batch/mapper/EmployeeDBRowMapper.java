package com.jcs.batch.mapper;

import com.jcs.batch.model.EmployeeDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeDBRowMapper implements RowMapper<EmployeeDTO> {
    @Override
    public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeDTO emp = new EmployeeDTO();
        emp.setEmpId(rs.getString("emp_id"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setEmail(rs.getString("email"));
        emp.setPhoneNo(rs.getString("phone_no"));
        emp.setHireDate(rs.getString("hire_date"));
        emp.setJobId(rs.getString("job_id"));
        emp.setSalary(rs.getString("salary"));
        emp.setNewSalary(rs.getDouble("new_salary"));
        return emp;
    }
}
