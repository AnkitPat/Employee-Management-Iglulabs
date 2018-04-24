package com.android.employeemanagement.model;

import com.android.employeemanagement.model.api.EmployeeApi;
import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.model.db.EmployeeDao;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by suraj on 14/4/18.
 */

public class EmployeeRepository {

    EmployeeDao employeeDao;
    EmployeeApi employeeApi;
    public EmployeeRepository(EmployeeApi employeeApi, EmployeeDao employeeDao)
    {
        this.employeeDao = employeeDao;
        this.employeeApi = employeeApi;
    }

    public Observable<List<Employee>> getEmployees() {
        return Observable.concatArray(
                getEmployeeFromDb(),
                getEmployeeFromApi()
        );
    }

    Observable<List<Employee>> getEmployeeFromDb() {
        return employeeDao.getEmployees().filter((Predicate)null).toObservable();
    }

    Observable<List<Employee>> getEmployeeFromApi() {
        return employeeApi.getEmployees().doOnNext(new Consumer<List<Employee>>() {
            @Override
            public void accept(List<Employee> employees) throws Exception {
                storeEmployeeInDb(employees);
            }
        });
    }


    void storeEmployeeInDb(final List<Employee> employees) {
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                employeeDao.insertAll(employees);
                return null;
            }
        });
    }






}
