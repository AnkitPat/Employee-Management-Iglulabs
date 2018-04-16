package com.android.employeemanagement.model;

import com.android.employeemanagement.model.api.EmployeeApi;
import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.model.data.SanctionedLeavesStatus;
import com.android.employeemanagement.model.db.EmployeeDao;
import com.android.employeemanagement.model.db.SanctionedLeaveDao;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by Ankit on 14/4/18.
 * 
 * Main Interface called as Repository which makes interface between Room Database and the View for fetching data
 */

public class EmployeeRepository {

    private EmployeeDao employeeDao;
    private EmployeeApi employeeApi;
    private SanctionedLeaveDao sanctionedLeaveDao;
    public EmployeeRepository(EmployeeApi employeeApi, EmployeeDao employeeDao, SanctionedLeaveDao sanctionedLeaveDao)
    {
        this.employeeDao = employeeDao;
        this.employeeApi = employeeApi;
        this.sanctionedLeaveDao = sanctionedLeaveDao;
    }

    public Observable<List<Employee>> getEmployees() {
        return Observable.concatArray(
                getEmployeeFromDb(),
                getEmployeeFromApi()
        );
    }

    private Observable<List<Employee>> getEmployeeFromDb() {
        return employeeDao.getEmployees().toObservable();
    }

    private Observable<List<Employee>> getEmployeeFromApi() {
        return employeeApi.getEmployees().doOnNext(new Consumer<List<Employee>>() {
            @Override
            public void accept(List<Employee> employees) throws Exception {
                storeEmployeeInDb(employees);
            }
        });
    }


    private void storeEmployeeInDb(final List<Employee> employees) {
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                employeeDao.insertAll(employees);
                return null;
            }
        });
    }
    
    
    public void storeLeaveSanctionedStatusInDb(SanctionedLeavesStatus sanctionedLeavesStatus) {
        sanctionedLeaveDao.insert(sanctionedLeavesStatus);
    }
    
    public List<SanctionedLeavesStatus> getLeaveSanctionedInfoByEmployee(String employee_id) {
        return sanctionedLeaveDao.getEmployeesById(employee_id);
    }






}
