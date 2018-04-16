package com.android.employeemanagement.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.model.data.SanctionedLeavesStatus;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ankit on 16/04/18.
 * 
 * Interface for interacting with the table named SanctionedLeave
 */

@Dao
public interface SanctionedLeaveDao {

    @Query("Select * from SanctionedLeavesStatus where employee_id like :employee_id")
    List<SanctionedLeavesStatus> getEmployeesById(String employee_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SanctionedLeavesStatus sanctionedLeaveInfo);
}
