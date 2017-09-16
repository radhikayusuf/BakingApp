package com.example.radhikayusuf.bakingapp.ui.fragment_detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.dao.StepsDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;

/**
 * @author radhikayusuf.
 */

public class ListStepVM extends BaseVM<RecipeDao> {

    public ListStepAdapter adapter;
    public LinearLayoutManager layoutManager;
    public ObservableField<String> img_url = new ObservableField<>("");

    public ListStepVM(Context mContext, RecipeDao data) {
        super(mContext, data);
        adapter = new ListStepAdapter(mData, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        img_url.set(mData.getImage());
    }

    public void onClickViewIngredients(View v){
        ((ListStepCallback)mContext).onClickViewIngredients();
    }

    public interface ListStepCallback{
        void onClickViewIngredients();

        void onClickItemStep(int position);
    }

}
