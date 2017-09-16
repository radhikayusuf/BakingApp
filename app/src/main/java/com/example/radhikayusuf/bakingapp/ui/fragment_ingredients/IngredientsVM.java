package com.example.radhikayusuf.bakingapp.ui.fragment_ingredients;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.example.radhikayusuf.bakingapp.dao.IngredientsDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;

import java.util.List;

/**
 * @author radhikayusuf.
 */

public class IngredientsVM extends BaseVM<List<IngredientsDao>>{

    public IngredientsAdapter adapter;
    public LinearLayoutManager layoutManager;

    public IngredientsVM(Context mContext, List<IngredientsDao> mData) {
        super(mContext, mData);
        adapter = new IngredientsAdapter(mData, mContext);
        layoutManager = new LinearLayoutManager(mContext);
    }

}
