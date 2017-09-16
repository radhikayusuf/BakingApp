package com.example.radhikayusuf.bakingapp.ui.main_recipe;

import android.content.Context;
import android.databinding.ObservableField;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;

/**
 * @author radhikayusuf.
 */

public class MainRowVM {

    public ObservableField<String> recipe_name = new ObservableField<>("");
    public ObservableField<String> ingredient_count = new ObservableField<>("");
    public ObservableField<String> steps_count = new ObservableField<>("");
    public ObservableField<String> serving = new ObservableField<>("");
    public ObservableField<String> image_url = new ObservableField<>("");

    public MainRowVM(RecipeDao recipeDao, Context context) {
        recipe_name.set(recipeDao.getName());
        ingredient_count.set(context.getString(R.string.ingredients_count, String.valueOf(recipeDao.getIngredients().size())));
        serving.set(context.getString(R.string.serving_count, String.valueOf(recipeDao.getIngredients().size())));
        steps_count.set(context.getString(R.string.steps_count, String.valueOf(recipeDao.getIngredients().size())));
        image_url.set(recipeDao.getImage());
    }
}
