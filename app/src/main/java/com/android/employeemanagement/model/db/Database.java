package com.android.employeemanagement.model.db;

import android.arch.persistence.room.RoomDatabase;

import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.model.data.SanctionedLeavesStatus;

/**
 * Created by Ankit on 14/4/18.
 *
 * Main Database class for creating the database in app
 */

@android.arch.persistence.room.Database(entities = {Employee.class, SanctionedLeavesStatus.class}, version = 1 )
public abstract class Database extends RoomDatabase {

    public abstract EmployeeDao getEmployeeDao();
    public abstract SanctionedLeaveDao getSanctionedLeaveDao();
}
