package com.example.radhikayusuf.bakingapp.ui.base;

import android.content.Context;

import rx.Subscription;

/**
 * @author radhikayusuf.
 */

public abstract class BaseVM<T> {

    private Subscription subscription;
    public Context mContext;
    public T mData;


    public BaseVM(Context mContext, T mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    public void addSubscription(Subscription s) {
        this.subscription = s;
    }

    public void unRegisterSubscription() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public BaseActivity getActivity(){
        return (BaseActivity)mContext;
    }


}
