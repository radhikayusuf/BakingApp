package com.example.radhikayusuf.bakingapp.ui.fullscreen_player;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.databinding.ActivityFullScreenPlayerBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseActivity;
import com.google.android.exoplayer2.util.Util;

public class FullScreenPlayerActivity extends BaseActivity<ActivityFullScreenPlayerBinding, FullScreenPlayerVM> {

    @Override
    protected FullScreenPlayerVM onPrepare(Bundle savedInstanceState) {

        FullScreenPlayerVM vm = new FullScreenPlayerVM(this, getIntent().getStringExtra("VID_URL"), getBinding());
        vm.setCurrentWindow(getIntent().getIntExtra("WINDOW_INDEX", 0));
        vm.setPlaybackPosition(getIntent().getLongExtra("STATE", 0));
        return vm;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_full_screen_player;
    }

    @Override
    protected ActivityFullScreenPlayerBinding onCreateVM(FullScreenPlayerVM vm, ActivityFullScreenPlayerBinding binding) {

        binding.setVm(vm);
        return binding;
    }

    @Override
    protected int getToolbarID() {
        return 0;
    }

    @Override
    protected void onToolbarReady(ActionBar acb) {

    }

    @Override
    protected DrawerLayout getDrawer() {
        return null;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.wtf("onSaveInstanceState: ", "onSave");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.wtf("onRestoreInstanceState: ", "restore");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            getVm().initPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVm().hideSystemUi();
        if ((Util.SDK_INT <= 23 || getVm().exoPlayer == null)) {
            getVm().initPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            getVm().releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            getVm().releasePlayer();
        }
    }
}
