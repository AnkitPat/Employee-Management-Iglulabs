package com.android.employeemanagement.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.employeemanagement.EmployeeApplication;
import com.android.employeemanagement.R;
import com.android.employeemanagement.adapter.EmployeeListAdapter;
import com.android.employeemanagement.view_model.EmployeeListViewModel;
import com.android.employeemanagement.view_model.data.EmployeeList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by suraj on 14/4/18.
 */

public class EmployeeListFragment extends MvvmFragment{

    EmployeeListViewModel employeeListViewModel = null;


    RecyclerView employeeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.employee_list_fragment,container,false);
        employeeRecyclerView = (RecyclerView)layoutView.findViewById(R.id.employee_list);

       employeeListViewModel =  EmployeeApplication.injectEmployeListViewModel();

        return layoutView;
    }

    @Override
    public void onStart() {
        super.onStart();

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

    public void showEmployee(EmployeeList employeeList) {
        if(employeeList.error==null) {
            employeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            employeeRecyclerView.setAdapter(new EmployeeListAdapter(getContext(),employeeList.employeeList));
        }
    }
}
