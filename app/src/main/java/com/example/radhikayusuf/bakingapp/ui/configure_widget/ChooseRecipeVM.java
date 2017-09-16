package com.example.radhikayusuf.bakingapp.ui.configure_widget;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.data.RecipeContract;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;
import com.example.radhikayusuf.bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author radhikayusuf.
 */

public class ChooseRecipeVM extends BaseVM<List<RecipeDao>>
    implements LoaderManager.LoaderCallbacks<Cursor>{

    private String TAG = ChooseRecipeVM.class.getName();
    public ChooseRecipeAdapter adapter;
    public LinearLayoutManager layoutManager;
    private LoaderManager mSupportLoader;
    private final int RECIPE_LOADER_ID = 0;

    public ChooseRecipeVM(Context mContext, LoaderManager supportLoaderManager) {
        super(mContext, new ArrayList<RecipeDao>());
        mSupportLoader = supportLoaderManager;
        mSupportLoader.initLoader(RECIPE_LOADER_ID, null, this);
        adapter = new ChooseRecipeAdapter(mContext, mData);
        layoutManager = new LinearLayoutManager(mContext);
        restartLoader();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(mContext) {
            Cursor mRecipeData = null;

            @Override
            protected void onStartLoading() {
                if(mRecipeData != null){
                    deliverResult(mRecipeData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return mContext.getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            RecipeContract.RecipeEntry._ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.\n"+ e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mRecipeData = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            mData.clear();
            for (int i = 0; i < data.getCount(); i++) {
                mData.add(Utils.cursorToRecipeDao(data, i));
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    public void restartLoader() {
        mSupportLoader.restartLoader(RECIPE_LOADER_ID, null, this);
    }
}
