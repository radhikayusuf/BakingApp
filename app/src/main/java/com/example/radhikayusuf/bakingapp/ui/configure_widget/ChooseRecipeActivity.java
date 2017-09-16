package com.example.radhikayusuf.bakingapp.ui.configure_widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.ActivityChooseRecipeBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseActivity;
import com.example.radhikayusuf.bakingapp.ui.main_recipe.MainActivity;
import com.example.radhikayusuf.bakingapp.ui.main_recipe.MainVM;
import com.example.radhikayusuf.bakingapp.utils.OnClickItemListener;
import com.example.radhikayusuf.bakingapp.utils.Utils;
import com.example.radhikayusuf.bakingapp.widget.IngredientsWidgetService;
import com.google.gson.Gson;

public class ChooseRecipeActivity extends BaseActivity<ActivityChooseRecipeBinding, ChooseRecipeVM>
    implements OnClickItemListener{


    private static final String EXTRA_APPWIDGET_ID = "CONFIGURE_DATA_WIDGET";
    private int mAppWidgetId;

    @Override
    protected ChooseRecipeVM onPrepare(Bundle savedInstanceState) {
        return new ChooseRecipeVM(this, getSupportLoaderManager());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_choose_recipe;
    }

    @Override
    protected ActivityChooseRecipeBinding onCreateVM(ChooseRecipeVM vm, ActivityChooseRecipeBinding binding) {
        binding.setVm(vm);

        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        return binding;
    }

    @Override
    protected int getToolbarID() {
        return R.id.toolbar;
    }

    @Override
    protected void onToolbarReady(ActionBar acb) {
        acb.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected DrawerLayout getDrawer() {
        return null;
    }

    @Override
    public void onClickItem(int position) {
        Utils.saveString(this, getString(R.string.pref_data_widget), new Gson().toJson(getVm().mData.get(position)));

        MainVM.updateWidget(this);
        Intent resultValue = new Intent(this, IngredientsWidgetService.class);
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
