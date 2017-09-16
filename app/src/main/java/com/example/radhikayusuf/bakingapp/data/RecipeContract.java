package com.example.radhikayusuf.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author radhikayusuf.
 */

public class RecipeContract {


    public static final String AUTHORITY = "com.example.radhikayusuf.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECIPE = "recipe";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "tb_recipe";

        public static final String COLUMN_RECIPE_IDS = "ids";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SERVING = "serving";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_STEP = "step";
    }
}
