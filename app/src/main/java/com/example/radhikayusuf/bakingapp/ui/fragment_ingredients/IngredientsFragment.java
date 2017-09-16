package com.example.radhikayusuf.bakingapp.ui.fragment_ingredients;


import android.support.v4.app.Fragment;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.FragmentIngredientsBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseFragment;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends BaseFragment<FragmentIngredientsBinding, IngredientsVM> {


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    protected IngredientsVM onPrepare() {
        return new IngredientsVM(getContext(), ((DetailActivity)getActivity()).mData.getIngredients());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ingredients;
    }

    @Override
    protected FragmentIngredientsBinding onCreateVM(IngredientsVM vm, FragmentIngredientsBinding binding) {
        binding.setVm(vm);
        return binding;
    }
}
