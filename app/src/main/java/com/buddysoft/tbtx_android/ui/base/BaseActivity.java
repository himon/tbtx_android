package com.buddysoft.tbtx_android.ui.base;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.app.component.AppProductionComponent;


/**
 * Created by Administrator on 2016/4/7.
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected abstract void setUpContentView();

    protected abstract void setUpView();

    protected abstract void setUpData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
        setUpContentView();
        setUpView();
        setUpData();
    }

    protected abstract void setupActivityComponent();

    public AppProductionComponent getAppProductionComponent() {
        return TbtxApplication.get(this).getAppProductionComponent();
    }
}
