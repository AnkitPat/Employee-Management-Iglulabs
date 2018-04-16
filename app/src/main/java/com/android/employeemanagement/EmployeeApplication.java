package com.android.employeemanagement;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.employeemanagement.model.EmployeeRepository;
import com.android.employeemanagement.model.api.EmployeeApi;
import com.android.employeemanagement.model.db.Database;
import com.android.employeemanagement.view_model.EmployeeListViewModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankit on 14/4/18.
 * 
 * Its the Base application for handling all the initial things going to be happen in the App.
 */

public class EmployeeApplication extends Application {

    private static EmployeeListViewModel employeeListViewModel=null;

    public static EmployeeListViewModel injectEmployeListViewModel() {
        return employeeListViewModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            //Retrofit fetching of webservice
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://s3.ap-south-1.amazonaws.com/checkankit/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            //getting data from web service
            EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

            //initialising the database
            Database database = Room.databaseBuilder(this, Database.class, "employee-database").build();

            EmployeeRepository employeeRepository = new EmployeeRepository(employeeApi, database.getEmployeeDao(), database.getSanctionedLeaveDao());

            employeeListViewModel = new EmployeeListViewModel(employeeRepository);
        }
        else {
            Toast.makeText(this,"Sorry not connected to internet",Toast.LENGTH_LONG).show();   
        }
    }
}
