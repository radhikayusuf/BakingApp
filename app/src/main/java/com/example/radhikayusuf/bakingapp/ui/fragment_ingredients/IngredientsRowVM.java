package com.example.radhikayusuf.bakingapp.ui.fragment_ingredients;

import android.content.Context;
import android.databinding.ObservableField;

import com.example.radhikayusuf.bakingapp.dao.IngredientsDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;

/**
 * @author radhikayusuf.
 */

public class IngredientsRowVM extends BaseVM<IngredientsDao> {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> qty = new ObservableField<>();

    public IngredientsRowVM(Context mContext, IngredientsDao mData) {
        super(mContext, mData);
        title.set(mData.getIngredient());
        qty.set(String.valueOf(mData.getQuantity()) +" "+ mData.getMeasure());
    }
}
