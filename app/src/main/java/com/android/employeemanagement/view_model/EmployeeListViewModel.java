package com.android.employeemanagement.view_model;


import com.android.employeemanagement.model.EmployeeRepository;
import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.view_model.data.EmployeeList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Ankit on 14/4/18
 * 
 * Its the basis view model for creating MVVM in the application.
 */

public class EmployeeListViewModel {

    public EmployeeRepository employeeRepository;
    public EmployeeListViewModel(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

   public Observable<EmployeeList> getEmployees() {

        return employeeRepository.getEmployees()
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(new Function<List<Employee>, EmployeeList>() {
                    @Override
                    public EmployeeList apply(List<Employee> employees) throws Exception {
                        return new EmployeeList(employees,"List of Emplouyess",null);

                    }
                });
    }
}