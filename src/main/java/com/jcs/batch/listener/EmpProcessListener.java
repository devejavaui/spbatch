package com.jcs.batch.listener;

import com.jcs.batch.model.EmployeeDTO;
import org.springframework.batch.core.ItemProcessListener;

public class EmpProcessListener implements ItemProcessListener<EmployeeDTO, EmployeeDTO> {
    @Override
    public void beforeProcess(EmployeeDTO item) {
        System.out.println("before processing:"+ item.toString());
    }

    @Override
    public void afterProcess(EmployeeDTO item, EmployeeDTO result) {
        System.out.println("after processing::"+ "item::"+item.toString() + "result::"+result.toString());
    }

    @Override
    public void onProcessError(EmployeeDTO item, Exception e) {
        System.out.println("on processing error:"+ item.toString() + e.getMessage());
    }
}
