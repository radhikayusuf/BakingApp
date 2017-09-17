package com.example.radhikayusuf.bakingapp.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.dao.RecipeDao;
import com.example.radhikayusuf.bakingapp.databinding.ActivityDetailBinding;
import com.example.radhikayusuf.bakingapp.ui.base.BaseActivity;
import com.example.radhikayusuf.bakingapp.ui.fragment_detail.ListStepFragment;
import com.example.radhikayusuf.bakingapp.ui.fragment_detail.ListStepVM;
import com.example.radhikayusuf.bakingapp.ui.fragment_ingredients.IngredientsFragment;
import com.example.radhikayusuf.bakingapp.ui.fragment_step_instuction.InstructionsFragment;
import com.example.radhikayusuf.bakingapp.utils.Utils;

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailVM>
        implements ListStepVM.ListStepCallback {

    public RecipeDao mData;
    private FragmentTransaction fragmentTransaction;
    private ListStepFragment fragmentListStep;
    private InstructionsFragment fragmentInstructions;
    private IngredientsFragment fragmentIngredients;

    public boolean isTab = false;

    private boolean isVideo = false;
    private boolean isIngredients = false;

    private boolean isCanInitVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mData = getIntent().getParcelableExtra(getString(R.string.recipe_intent));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DetailVM onPrepare(Bundle savedInstanceState) {
        isTab = Utils.isTablet(this);

        fragmentIngredients = (IngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.ingredient_fragment);
        fragmentListStep = (ListStepFragment) getSupportFragmentManager().findFragmentById(R.id.list_step_fragment);
        fragmentInstructions = (InstructionsFragment) getSupportFragmentManager().findFragmentById(R.id.instruction_fragment);
        return new DetailVM(this, mData);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected ActivityDetailBinding onCreateVM(DetailVM vm, ActivityDetailBinding binding) {
        binding.setVm(vm);
        binding.navView.setNavigationItemSelectedListener(this);

        //setRequestedOrientation(!isTab ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        return binding;
    }

    @Override
    protected int getToolbarID() {
        return R.id.toolbar;
    }

    @Override
    protected void onToolbarReady(ActionBar acb) {
        acb.setTitle(getVm().mData.getName());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, getBinding().drawerDetail, getBinding().toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getBinding().drawerDetail.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected DrawerLayout getDrawer() {
        return getBinding().drawerDetail;
    }

    public void showFragment(Fragment fragment, boolean backAnim) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(
                backAnim ? R.anim.slide_in_left : R.anim.slide_in_right,
                backAnim ? R.anim.slide_out_right : R.anim.slide_out_left);
        fragmentTransaction.hide(fragmentIngredients);
        fragmentTransaction.hide(fragmentListStep);
        fragmentTransaction.hide(fragmentInstructions);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    @Override
    public void onClickViewIngredients() {
        isCanInitVideo = false;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragmentInstructions);

        if (getBinding().identifierLayoutTab != null) {
            fragmentTransaction.show(fragmentListStep);
            fragmentInstructions.getVm().releasePlayer();
        } else {
            fragmentTransaction.hide(fragmentListStep);
        }

        fragmentTransaction.show(fragmentIngredients);
        fragmentTransaction.commit();
        isIngredients = true;
    }

    @Override
    public void onClickItemStep(int position) {
        isCanInitVideo = true;
        if ((getBinding().identifierLayoutTab != null) && !Utils.isPotrait(this)) {
            fragmentInstructions.getVm().setPlaybackPosition(0);
            fragmentInstructions.changeIndexStep(position);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentIngredients);
            fragmentTransaction.show(fragmentInstructions);
            fragmentTransaction.commit();
        } else {
            fragmentInstructions.getVm().setPlaybackPosition(0);
            showFragment(fragmentInstructions, false);
            fragmentInstructions.changeIndexStep(position);
            isVideo = true;
        }
    }


    @Override
    public void onBackPressed() {
        if (isVideo) {
            fragmentInstructions.getVm().releasePlayer();
            showFragment(fragmentListStep, true);
            isVideo = !isVideo;
        } else if (isIngredients) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentIngredients);

            if (getBinding().identifierLayoutTab != null) {
                fragmentTransaction.show(fragmentInstructions);
            } else {
                fragmentTransaction.hide(fragmentInstructions);
            }
            fragmentTransaction.show(fragmentListStep);
            fragmentTransaction.commit();
            isIngredients = !isIngredients;
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isTab", isTab);
        outState.putBoolean("isVideo", isVideo);
        outState.putBoolean("isIngredients", isIngredients);
        outState.putBoolean("isCanInitVideo", isCanInitVideo);
        outState.putInt("instructionsIndex", fragmentInstructions.getIndexStep());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isTab = savedInstanceState.getBoolean("isTab", false);
        isVideo = savedInstanceState.getBoolean("isVideo", false);
        isIngredients = savedInstanceState.getBoolean("isIngredients", false);
        isCanInitVideo = savedInstanceState.getBoolean("isCanInitVideo", false);

        if (isVideo) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentIngredients);
            fragmentTransaction.show(fragmentListStep);
            fragmentTransaction.show(fragmentInstructions);
            fragmentTransaction.commit();

            fragmentInstructions.changeIndexStep(savedInstanceState.getInt("instructionsIndex"));
            isVideo = false;
        }
        if(isIngredients){
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentInstructions);
            fragmentTransaction.show(fragmentIngredients);
            fragmentTransaction.show(fragmentListStep);
            fragmentTransaction.commit();

            fragmentInstructions.changeIndexStep(savedInstanceState.getInt("instructionsIndex"));
            isIngredients = false;
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isCanInitVideo() {
        return isCanInitVideo;
    }
}
