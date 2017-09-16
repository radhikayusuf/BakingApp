package com.example.radhikayusuf.bakingapp.ui.detail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;
import com.example.radhikayusuf.bakingapp.ui.fragment_detail.ListStepAdapter;
import com.example.radhikayusuf.bakingapp.ui.fragment_detail.ListStepFragment;

/**
 * @author radhikayusuf.
 */

public class DetailVM extends BaseVM<RecipeDao> {

    public DetailVM(Context mContext, RecipeDao data) {
        super(mContext, data);
    }

}
