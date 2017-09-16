package com.example.radhikayusuf.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.ui.main_recipe.MainVM;
import com.example.radhikayusuf.bakingapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author radhikayusuf.
 */

public class IngredientsViewFactory
        implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext = null;
    private int appWidgetId;
    private RecipeDao mData;

    public IngredientsViewFactory(Context context, Intent intent) {
        this.mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
//        mData = new ArrayList<>();

    }

    @Override
    public void onCreate() {
        Type ingType = new TypeToken<RecipeDao>() {
        }.getType();
        mData = new Gson().fromJson(
                Utils.getString(mContext, mContext.getString(R.string.pref_data_widget)),
                ingType);
    }

    @Override
    public void onDataSetChanged() {
        Type ingType = new TypeToken<RecipeDao>() {
        }.getType();
        mData = new Gson().fromJson(
                Utils.getString(mContext, mContext.getString(R.string.pref_data_widget)),
                ingType);
//        if (mData != null) mData.close();
//
//        final long identityToken = Binder.clearCallingIdentity();
//        mData = context.getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);
//        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mData != null) {
            if (mData.getIngredients() != null) {
                return mData.getIngredients().size();
            }
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || mData == null) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_row_ingredient);


        remoteViews.setTextViewText(R.id.textTitle, mData.getIngredients().get(position).getIngredient());
        remoteViews.setTextViewText(R.id.textQTY, mData.getIngredients().get(position).getQuantity() + " " + mData.getIngredients().get(position).getMeasure());

        final Intent fillInIntent = new Intent(mContext, DetailActivity.class);

        fillInIntent.putExtra(mContext.getString(R.string.recipe_intent), mData);

        remoteViews.setOnClickFillInIntent(R.id.list_widget_root, fillInIntent);
//        RemoteViews row=new RemoteViews(context.getPackageName(),
//                R.layout.row_recipe);
//
//        row.setTextViewText(android.R.id.text1, mData.get(0).getIngredients().get(position).getIngredient());
//
//        Intent intent =new Intent();
//        Bundle extras=new Bundle();
//
//        extras.putString(IngredientsWidgetProvider.EXTRA_WORD, mData.get(0).getIngredients().get(position).getIngredient());
//        intent.putExtras(extras);
//        row.setOnClickFillInIntent(android.R.id.text1, intent);
        return (remoteViews);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
