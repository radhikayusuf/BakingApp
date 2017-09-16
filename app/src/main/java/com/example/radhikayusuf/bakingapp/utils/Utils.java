package com.example.radhikayusuf.bakingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.example.radhikayusuf.bakingapp.dao.IngredientsDao;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.dao.StepsDao;
import com.example.radhikayusuf.bakingapp.data.RecipeContract;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author radhikayusuf.
 */

public class Utils {

    public static boolean isTablet(Activity act) {
        return (act.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static RecipeDao cursorToRecipeDao(Cursor cursor, int position){

        Type ingType = new TypeToken<List<IngredientsDao>>(){}.getType();
        Type stepType = new TypeToken<List<StepsDao>>(){}.getType();

        int idIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_IDS);
        int nameIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME);
        int imageIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_IMAGE);
        int servingIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_SERVING);
        int ingredientsIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS);
        int stepIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_STEP);

        cursor.moveToPosition(position);

        List<IngredientsDao> ingredientsDaos = new Gson().fromJson(cursor.getString(ingredientsIndex), ingType);
        List<StepsDao> steDaos = new Gson().fromJson(cursor.getString(stepIndex), stepType);

        RecipeDao recipeDao = new RecipeDao(
                cursor.getInt(idIndex),
                cursor.getInt(servingIndex),
                cursor.getString(nameIndex),
                cursor.getString(imageIndex),
                ingredientsDaos,
                steDaos);

        return recipeDao;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void saveString(Context context, String key, String value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, "");
    }
}
