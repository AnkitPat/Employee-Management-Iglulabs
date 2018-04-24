package com.android.employeemanagement.view_model.data;

import com.android.employeemanagement.model.data.Employee;

import java.util.List;

/**
 * Created by Ankit on 14/4/18.
 * 
 * It is supporting class for the Employee List which store list , error message and error
 */

public class EmployeeList {

    public List<Employee> employeeList;
    private String message;
    public Throwable error;

    public EmployeeList(List<Employee> employeeList, String message, Throwable error) {
        this.employeeList = employeeList;
        this.message = message;
        this.error = error;
    }
}
