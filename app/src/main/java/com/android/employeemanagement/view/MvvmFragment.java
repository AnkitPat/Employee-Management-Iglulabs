package com.android.employeemanagement.view;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Parent Fragment of all the Fragment 
 */
public class MvvmFragment extends Fragment {

    CompositeDisposable subscriptions = new CompositeDisposable();

    Disposable subscribe(Disposable disposable) {
        subscriptions.add(disposable);
        return disposable;
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }
}
