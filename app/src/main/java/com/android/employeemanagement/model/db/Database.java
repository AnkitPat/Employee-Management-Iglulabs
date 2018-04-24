package com.android.employeemanagement.model.db;

import android.arch.persistence.room.RoomDatabase;

import com.android.employeemanagement.model.data.Employee;

/**
 * Created by suraj on 14/4/18.
 */

@android.arch.persistence.room.Database(entities = Employee.class, version = 1 )
public abstract class Database extends RoomDatabase {

    public abstract EmployeeDao getEmployeeDao();
}
