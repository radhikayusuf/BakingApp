package com.example.radhikayusuf.bakingapp.ui.configure_widget;

import android.content.Context;
import android.databinding.ObservableField;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;

/**
 * @author radhikayusuf.
 */

public class ChooseRecipeRowVM extends BaseVM<RecipeDao> {

    public ObservableField<String> recipe_name = new ObservableField<>("");
    public ObservableField<String> ingredient_count = new ObservableField<>("");
    public ObservableField<String> steps_count = new ObservableField<>("");
    public ObservableField<String> serving = new ObservableField<>("");
    public ObservableField<String> image_url = new ObservableField<>("");

    public ChooseRecipeRowVM(Context mContext, RecipeDao mData) {
        super(mContext, mData);
        recipe_name.set(mData.getName());
        ingredient_count.set(mContext.getString(R.string.ingredients_count, String.valueOf(mData.getIngredients().size())));
        serving.set(mContext.getString(R.string.serving_count, String.valueOf(mData.getIngredients().size())));
        steps_count.set(mContext.getString(R.string.steps_count, String.valueOf(mData.getIngredients().size())));
        image_url.set(mData.getImage());
    }


}
