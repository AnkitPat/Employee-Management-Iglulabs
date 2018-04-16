package com.android.employeemanagement.model.api;

import com.android.employeemanagement.model.data.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Ankit on 15/4/18.
 * 
 * Interface for calling Retrofit services
 */

public interface EmployeeApi {
     @GET("employee_list.json")
    Observable<List<Employee>> getEmployees();
}
