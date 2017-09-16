package com.example.radhikayusuf.bakingapp.ui.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.radhikayusuf.bakingapp.R;

/**
 * @author radhikayusuf.
 */

public abstract class BaseActivity<B extends ViewDataBinding, VM extends BaseVM> extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private B binding;
    private VM vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        vm = onPrepare(savedInstanceState);
        binding = onCreateVM(vm, binding);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar)findViewById(getToolbarID()));
        onToolbarReady(getSupportActionBar());
    }

    @Override
    protected void onDestroy() {
        vm.unRegisterSubscription();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home_menu:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
        }

        getDrawer().closeDrawer(GravityCompat.START);
        return true;
    }


    public void openActivity(Class cls, Bundle b){
        Intent i = new Intent(this, cls);
        if(b!=null) i.putExtras(b);
        startActivity(i);
    }


    public B getBinding() {
        return binding;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    /**
     * abstract class
     * @param savedInstanceState
     */



    protected abstract VM onPrepare(Bundle savedInstanceState);

    protected abstract int getLayoutRes();

    protected abstract B onCreateVM(VM vm, B binding);

    protected abstract int getToolbarID();

    protected abstract void onToolbarReady(ActionBar acb);

    protected abstract DrawerLayout getDrawer();
}
