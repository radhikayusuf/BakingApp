package com.example.radhikayusuf.bakingapp.ui.fragment_detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;


/**
 * @author radhikayusuf.
 */

public class ListRowStep extends BaseVM<RecipeDao>{

    private final ListStepVM.ListStepCallback evt;
    public ObservableField<String> bTitle = new ObservableField<>();
    private int position;

    public ListRowStep(Context mContext, RecipeDao data, int position) {
        super(mContext, data);
        this.position = position;
        bTitle.set(mData.getSteps().get(position).getShortDescription());
        this.evt = (ListStepVM.ListStepCallback)mContext;
    }

    public void onClickStep(View v){
        evt.onClickItemStep(position);
    }
}
