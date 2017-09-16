package com.example.radhikayusuf.bakingapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author radhikayusuf.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipeDB.db";

    private static final int VERSION = 1;
    private Object allData;

    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + "("+
                RecipeContract.RecipeEntry._ID                    + " INTEGER PRIMARY KEY, "+
                RecipeContract.RecipeEntry.COLUMN_RECIPE_IDS      + " INTEGER NOT NULL, "+
                RecipeContract.RecipeEntry.COLUMN_SERVING         + " INTEGER NOT NULL, "+
                RecipeContract.RecipeEntry.COLUMN_NAME            + " TEXT NOT NULL, "+
                RecipeContract.RecipeEntry.COLUMN_IMAGE           + " TEXT NOT NULL, "+
                RecipeContract.RecipeEntry.COLUMN_INGREDIENTS     + " TEXT NOT NULL, "+
                RecipeContract.RecipeEntry.COLUMN_STEP            + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteAllData(){
        getWritableDatabase().delete(RecipeContract.RecipeEntry.TABLE_NAME, null, null);
    }

    public Cursor getAllData() {
//        return getWritableDatabase().query(RecipeContract.RecipeEntry.TABLE_NAME, null, null, null);
        return getWritableDatabase().query(RecipeContract.RecipeEntry.TABLE_NAME, null, null, null, null, null, null);
    }
}
