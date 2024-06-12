package com.jcs.batch.repository;

import com.jcs.batch.model.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeDTO, String> {
}
