package com.android.employeemanagement.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ankit on 16/04/18.
 * 
 * Room Table for storing leave information
 */

@Entity
public class SanctionedLeavesStatus {
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "leave_id")
    
    public int leave_id;
    
    @ColumnInfo(name = "employee_id")
    public String employee_id;
    
    @ColumnInfo(name = "leave_from_date")
    public String leave_from_date;

    @ColumnInfo(name = "leave_to_date")
    public String leave_to_date;
    
    @ColumnInfo(name = "leave_status")
    public String leave_status;
    
    @ColumnInfo(name = "leave_reason")
    public String leave_reason;
    
    
}
