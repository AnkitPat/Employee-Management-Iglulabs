package com.android.employeemanagement.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.employeemanagement.model.data.Employee;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ankit on 14/4/18.
 * 
 * Interface for making a layer with methods to interact with the database
 */

@Dao
public interface EmployeeDao {

    @Query("Select * from employee")
    Single<List<Employee>> getEmployees();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Employee> employees);
}
