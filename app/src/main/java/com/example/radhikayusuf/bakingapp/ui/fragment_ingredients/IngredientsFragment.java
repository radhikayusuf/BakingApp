package com.example.radhikayusuf.bakingapp.ui.fragment_ingredients;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentIngredientsBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends BaseFragment<FragmentIngredientsBinding, IngredientsVM> {


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    protected IngredientsVM onPrepare() {
        return new IngredientsVM(getContext(), ((DetailActivity) getActivity()).mData.getIngredients());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ingredients;
    }

    @Override
    protected FragmentIngredientsBinding onCreateVM(IngredientsVM vm, FragmentIngredientsBinding binding) {
        binding.setVm(vm);
        return binding;
    }

    private void scrollRecycler(final int i) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBinding().recyclerIngredients.scrollToPosition(i);
            }
        }, 200);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        int state = getVm().layoutManager.findFirstVisibleItemPosition();
        outState.putInt(getString(R.string.savedinstance_recyclerview_state), state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            scrollRecycler(savedInstanceState.getInt(getString(R.string.savedinstance_recyclerview_state)));
        }
        super.onViewStateRestored(savedInstanceState);
    }
}
