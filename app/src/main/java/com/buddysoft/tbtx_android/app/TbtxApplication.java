package com.buddysoft.tbtx_android.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDex;

import com.buddysoft.tbtx_android.app.component.AppComponent;
import com.buddysoft.tbtx_android.app.component.AppProductionComponent;
import com.buddysoft.tbtx_android.app.component.DaggerAppComponent;
import com.buddysoft.tbtx_android.app.component.DaggerAppProductionComponent;
import com.buddysoft.tbtx_android.app.component.UserComponent;
import com.buddysoft.tbtx_android.app.module.AppModule;
import com.buddysoft.tbtx_android.app.module.UserModule;
import com.videogo.openapi.EZOpenSDK;

import java.util.concurrent.Executors;

/**
 * Created by lc on 16/4/9.
 */
public class TbtxApplication extends Application {

    public static String APP_KEY = "0b9f4a7b93e4454e9eb3dc1a91a16825";//视频直播

    private AppComponent mAppComponent;
    private AppProductionComponent mAppProductionComponent;
    private UserComponent mUserComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public AppProductionComponent getAppProductionComponent() {
        return mAppProductionComponent;
    }

    public UserComponent getUserComponent() {
        return mUserComponent;
    }

    public static TbtxApplication get(Context context) {
        return (TbtxApplication) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        EZOpenSDK.initLib(this, APP_KEY, "");
        initAppComponent();
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mAppProductionComponent = DaggerAppProductionComponent.builder()
                .executor(Executors.newSingleThreadExecutor())
                .appComponent(mAppComponent)
                .build();
    }

    public UserComponent createUserComponent(UserModule userModule) {
        mUserComponent = mAppComponent.plus(userModule);
        return mUserComponent;
    }
}
