package com.android.employeemanagement.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by suraj on 14/4/18.
 */
@Entity
public class Employee {
     @PrimaryKey
    @ColumnInfo(name= "employee_id")
    @android.support.annotation.NonNull
    public String employee_id;

     @ColumnInfo(name="employee_name")
   public String employee_name;

     @ColumnInfo(name="profile_url")
    public String profile_url;


}
