package com.android.employeemanagement.model.api;

import com.android.employeemanagement.model.data.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by suraj on 14/4/18.
 */

public interface EmployeeApi {
     @GET("employee_list.json")
    Observable<List<Employee>> getEmployees();
}
