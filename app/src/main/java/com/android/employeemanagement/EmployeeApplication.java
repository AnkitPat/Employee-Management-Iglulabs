package com.android.employeemanagement;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.employeemanagement.model.EmployeeRepository;
import com.android.employeemanagement.model.api.EmployeeApi;
import com.android.employeemanagement.model.db.Database;
import com.android.employeemanagement.view_model.EmployeeListViewModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by suraj on 14/4/18.
 */

public class EmployeeApplication extends Application {

    private Retrofit retrofit = null;
    private EmployeeApi employeeApi=null;
    private EmployeeRepository employeeRepository=null;
    private static EmployeeListViewModel employeeListViewModel=null;
    private Database database;

    public static EmployeeListViewModel injectEmployeListViewModel() {
        return employeeListViewModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        employeeApi = retrofit.create(EmployeeApi.class);
        database = Room.databaseBuilder(this,Database.class,"employee-database").build();

        employeeRepository = new EmployeeRepository(employeeApi,database.getEmployeeDao());

        employeeListViewModel = new EmployeeListViewModel(employeeRepository);
    }
}
