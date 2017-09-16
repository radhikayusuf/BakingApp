package com.example.radhikayusuf.bakingapp.ui.fragment_detail;


import android.support.v4.app.Fragment;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentListStepBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListStepFragment extends BaseFragment<FragmentListStepBinding, ListStepVM> {


    public ListStepFragment() {
        // Required empty public constructor
    }


    @Override
    protected ListStepVM onPrepare() {
        DetailActivity act = ((DetailActivity)getActivity());
        return new ListStepVM(getContext(), act.mData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_step;
    }

    @Override
    protected FragmentListStepBinding onCreateVM(ListStepVM vm, FragmentListStepBinding binding) {
        binding.setVm(vm);
        binding.recyclerStep.setNestedScrollingEnabled(false);
        return binding;
    }
}
