package com.android.employeemanagement.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Ankit on 14/4/18.
 * 
 * Room Table named as Employee for saving employee information
 */
@Entity
public class Employee implements Serializable {
     @PrimaryKey
    @ColumnInfo(name= "employee_id")
    @android.support.annotation.NonNull
    public String employee_id;

     @ColumnInfo(name="employee_name")
   public String employee_name;

     @ColumnInfo(name="profile_url")
    public String profile_url;
     
     @ColumnInfo(name="employee_designation")
    public String employee_designation;
     
     @ColumnInfo(name="employee_age")
    public  String employee_age;
     
     @ColumnInfo(name="employee_gender")
    public  String employee_gender;


}
