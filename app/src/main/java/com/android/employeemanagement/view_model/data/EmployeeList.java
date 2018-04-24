package com.android.employeemanagement.view_model.data;

import com.android.employeemanagement.model.data.Employee;

import java.util.List;

/**
 * Created by suraj on 14/4/18.
 */

public class EmployeeList {

    public List<Employee> employeeList;
    public String message;
    public Throwable error;

    public EmployeeList(List<Employee> employeeList, String message, Throwable error) {
        this.employeeList = employeeList;
        this.message = message;
        this.error = error;
    }
}
