package com.buddysoft.tbtx_android.ui.base;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.app.component.AppProductionComponent;
import com.buddysoft.tbtx_android.widgets.CustomerProgress;


/**
 * Created by Administrator on 2016/4/7.
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected abstract void setUpContentView();

    protected abstract void setUpView();

    protected abstract void setUpData();

    private CustomerProgress mCustomerProgress;

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

    public void waittingDialog() {
        setTheme(android.R.style.Theme);
        mCustomerProgress = new CustomerProgress(this, "进行中,请稍后");
        mCustomerProgress.show();
    }

    public void stopCusDialog() {
        if (mCustomerProgress != null) {
            mCustomerProgress.dismiss();
        }
    }
}
