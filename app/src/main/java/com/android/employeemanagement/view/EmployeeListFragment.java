package com.android.employeemanagement.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.employeemanagement.EmployeeApplication;
import com.android.employeemanagement.R;
import com.android.employeemanagement.adapter.EmployeeListAdapter;
import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.view_model.EmployeeListViewModel;
import com.android.employeemanagement.view_model.data.EmployeeList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ankit on 15/4/18.
 * 
 * Main List Fragment where actually list is getting populated
 */

public class EmployeeListFragment extends MvvmFragment{

    EmployeeListViewModel employeeListViewModel = null;


    RecyclerView employeeRecyclerView;
    
    TextView loadingTextView;
    ProgressBar loadingProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.employee_list_fragment,container,false);
        employeeRecyclerView = (RecyclerView)layoutView.findViewById(R.id.employee_list);
        loadingTextView = layoutView.findViewById(R.id.loadingTextView);
        loadingProgressBar = layoutView.findViewById(R.id.loadingProgressBar);

        employeeRecyclerView.setVisibility(View.GONE);
        
        
       employeeListViewModel =  EmployeeApplication.injectEmployeListViewModel();

        return layoutView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            subscribe(employeeListViewModel.getEmployees()
                .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<EmployeeList>() {
                        @Override
                        public void accept(EmployeeList employeeList) throws Exception {
                            showEmployee(employeeList);
                        }
                    })
            );
        }
        else
        {
            loadingTextView.setVisibility(View.INVISIBLE);
            loadingProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    
    //Method to show the employee list
    public void showEmployee(EmployeeList employeeList) {
        if(employeeList.error==null) {

            if (employeeList.employeeList.size()>0) {
                loadingProgressBar.setVisibility(View.INVISIBLE);
                loadingTextView.setVisibility(View.INVISIBLE);
                employeeRecyclerView.setVisibility(View.VISIBLE);
            }
            employeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            employeeRecyclerView.setAdapter(new EmployeeListAdapter(getContext(),employeeList.employeeList));
        }
    }

   
}
