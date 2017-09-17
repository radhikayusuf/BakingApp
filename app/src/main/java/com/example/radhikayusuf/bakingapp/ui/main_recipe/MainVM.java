package com.example.radhikayusuf.bakingapp.ui.main_recipe;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.api.ApiClient;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.data.RecipeContract;
import com.example.radhikayusuf.bakingapp.data.RecipeDBHelper;
import com.example.radhikayusuf.bakingapp.ui.base.BaseVM;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.utils.OnClickItemListener;
import com.example.radhikayusuf.bakingapp.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.concurrency.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.radhikayusuf.bakingapp.widget.IngredientsWidgetProvider.ACTION_DATA_UPDATED;

/**
 * @author radhikayusuf.
 */

public class MainVM extends BaseVM<List<RecipeDao>>
        implements OnClickItemListener, LoaderManager.LoaderCallbacks<Cursor>{

    private LoaderManager mSupportLoader;
    public MainAdapter adapter;
    public GridLayoutManager layoutManager;
    private final int RECIPE_LOADER_ID = 0;
    private Gson mGson;
    private List<ContentValues> contentValuesList = new ArrayList<>();
    private String TAG = MainVM.class.getName();

    public MainVM(Context context, boolean isTab, boolean onCreateNullinstance, LoaderManager supportLoaderManager) {
        super(context, new ArrayList<RecipeDao>());
        adapter = new MainAdapter(mData, context, this);
        mSupportLoader = supportLoaderManager;
        mSupportLoader.initLoader(RECIPE_LOADER_ID, null, this);
        mContext = context;
        mGson = new Gson();
        layoutManager = new GridLayoutManager(context, isTab ? 3 : 1);
        if(onCreateNullinstance){
            ((MainActivity)mContext).setRefreshingSwipe(true);

            loadDataWithCheckConnection();
        }
    }

    public void loadDataWithCheckConnection() {
        if (Utils.isNetworkConnected(mContext)) {
            loadData();
        }else{
            if(mData.size() == 0){
                restartLoader();
            }
        }
    }

    private void loadData() {
        if (!((MainActivity)mContext).isSwipeRefreshing()) {
            ((MainActivity)mContext).setRefreshingSwipe(true);
        }

        contentValuesList.clear();

        addSubscription(ApiClient.service().getRecipes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<RecipeDao>, Observable<RecipeDao>>() {
                    @Override
                    public Observable<RecipeDao> call(List<RecipeDao> recipeDaos) {
                        contentValuesList.clear();
                        return Observable.from(recipeDaos);
                    }
                })
                .subscribe(new Subscriber<RecipeDao>() {
                    @Override
                    public void onCompleted() {

                        RecipeDBHelper helper = new RecipeDBHelper(mContext);
                        helper.deleteAllData();
                        ContentValues[] contentValuesArray = new ContentValues[contentValuesList.size()];
                        contentValuesArray = contentValuesList.toArray(contentValuesArray);
                        mContext.getContentResolver().bulkInsert(RecipeContract.RecipeEntry.CONTENT_URI, contentValuesArray);
                        loadDataLocal();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((MainActivity)mContext).setRefreshingSwipe(false);
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RecipeDao recipeDao) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_IDS, recipeDao.getId());
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_SERVING, recipeDao.getServings());
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_NAME, recipeDao.getName());
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, recipeDao.getImage());
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS, mGson.toJson(recipeDao.getIngredients()));
                        contentValues.put(RecipeContract.RecipeEntry.COLUMN_STEP, mGson.toJson(recipeDao.getSteps()));

                        contentValuesList.add(contentValues);
                    }
                }));
    }

    private void loadDataLocal() {
        restartLoader();
        updateWidget(mContext);
    }

    public static void updateWidget(Context context) {
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

    public List<RecipeDao> getData() {
        return mData;
    }

    public void setData(List<RecipeDao> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        adapter.notifyDataSetChanged();
    }

    public void onRefresh(){
        mData.clear();
        loadData();
    }

    @Override
    public void onClickItem(int position) {
        Bundle b = new Bundle();
        if(mData.size() > 0){
            b.putParcelable(mContext.getString(R.string.recipe_intent), mData.get(position));
            getActivity().openActivity(DetailActivity.class, b);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            mData.clear();
            for (int i = 0; i < data.getCount(); i++) {
                mData.add(Utils.cursorToRecipeDao(data, i));
            }

            adapter.notifyDataSetChanged();
            ((MainActivity)mContext).setRefreshingSwipe(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void restartLoader() {
        mSupportLoader.restartLoader(RECIPE_LOADER_ID, null, this);
    }
}
