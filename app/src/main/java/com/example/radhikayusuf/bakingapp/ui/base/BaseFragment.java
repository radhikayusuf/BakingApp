package com.example.radhikayusuf.bakingapp.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author radhikayusuf.
 */

public abstract class BaseFragment<B extends ViewDataBinding, VM extends BaseVM> extends Fragment {

    private B binding;
    private VM vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        vm = onPrepare();
        binding = onCreateVM(vm, binding);
        return binding.getRoot();
    }


    public B getBinding() {
        return binding;
    }

    public void setBinding(B binding) {
        this.binding = binding;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    protected abstract VM onPrepare();

    //TODO change ID
    protected abstract int getLayoutRes();

    protected abstract B onCreateVM(VM vm, B binding);
}
