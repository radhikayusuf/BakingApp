package com.example.radhikayusuf.bakingapp.ui.main_recipe;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.ActivityMainBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseActivity;
import com.example.radhikayusuf.bakingapp.utils.NetworkReceiver;
import com.example.radhikayusuf.bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainVM> implements NetworkReceiver.ConnectivityReceiverListener {


    private NetworkReceiver receiver;

    @Override
    protected MainVM onPrepare(Bundle savedInstanceState) {

        return new MainVM(this, Utils.isTablet(this), savedInstanceState == null, getSupportLoaderManager());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected ActivityMainBinding onCreateVM(MainVM vm, ActivityMainBinding binding) {
        binding.setVm(getVm());
        binding.navView.setNavigationItemSelectedListener(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        receiver.setConnectivityReceiverListener(this);
        registerReceiver(receiver, filter);

        return binding;
    }

    @Override
    protected DrawerLayout getDrawer() {
        return getBinding().drawerMain;
    }

    @Override
    protected int getToolbarID() {
        return R.id.toolbar;
    }

    @Override
    protected void onToolbarReady(ActionBar acb) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, getBinding().drawerMain, getBinding().toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getBinding().drawerMain.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        List<RecipeDao> data = savedInstanceState.getParcelableArrayList(getString(R.string.key_data_main));
        getVm().setData(data);
        scrollRecycler(savedInstanceState.getInt(getString(R.string.savedinstance_recyclerview_state)));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.key_data_main), (ArrayList<? extends Parcelable>) getVm().getData());
        int state = getVm().layoutManager.findFirstVisibleItemPosition();
        outState.putInt(getString(R.string.savedinstance_recyclerview_state), state);
        super.onSaveInstanceState(outState);
    }

    private void scrollRecycler(final int i) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBinding().recyclerMain.scrollToPosition(i);
            }
        }, 200);
    }

    public void setRefreshingSwipe(boolean swipe) {
        getBinding().swipeMain.setRefreshing(swipe);
    }

    @Override
    public void onBackPressed() {
        if (getBinding().drawerMain.isDrawerOpen(GravityCompat.START)) {
            getBinding().drawerMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVm().restartLoader();
        Log.wtf("onResume: ", String.valueOf(Utils.isNetworkConnected(this)));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.wtf("onNetworkConnectionChanged: ", String.valueOf(isConnected));
        if (isConnected) {
            getVm().loadDataWithCheckConnection();
        }
    }

    public boolean isSwipeRefreshing() {
        return getBinding().swipeMain.isRefreshing();
    }
}
