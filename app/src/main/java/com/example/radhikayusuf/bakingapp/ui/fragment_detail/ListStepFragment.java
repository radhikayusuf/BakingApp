package com.example.radhikayusuf.bakingapp.ui.fragment_detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentListStepBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.utils.MyCustomScrollView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListStepFragment
        extends BaseFragment<FragmentListStepBinding, ListStepVM>
        implements MyCustomScrollView.MyCustomScrollViewListener{


    private int stateScroll = 0;

    public ListStepFragment() {
        // Required empty public constructor
    }


    @Override
    protected ListStepVM onPrepare() {
        DetailActivity act = ((DetailActivity)getActivity());
        return new ListStepVM(getContext(), act.mData);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list_step;
    }

    @Override
    protected FragmentListStepBinding onCreateVM(ListStepVM vm, FragmentListStepBinding binding) {
        binding.setVm(vm);
        binding.recyclerStep.setNestedScrollingEnabled(false);
        binding.scrollListStep.setScrollViewListener(this);
        return binding;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("scroll_state_detail", stateScroll);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            int state = savedInstanceState.getInt("scroll_state_detail");
            getBinding().scrollListStep.scrollTo(0, getBinding().scrollListStep.getHeight() * state);
        }
        super.onViewStateRestored(savedInstanceState);
    }


    @Override
    public void onScrollChanged(MyCustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        Log.wtf("onScrollChanged: ", "x : "+x+"\ny : "+y+"\noldX : "+oldx+"\noldy : "+oldy);
        stateScroll = oldy / scrollView.getHeight();
    }
}
