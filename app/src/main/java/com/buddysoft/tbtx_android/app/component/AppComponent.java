package com.buddysoft.tbtx_android.app.component;

import android.app.Application;


import com.buddysoft.tbtx_android.app.module.AppModule;
import com.buddysoft.tbtx_android.app.module.UserModule;
import com.buddysoft.tbtx_android.ui.component.LoginActivityComponent;
import com.buddysoft.tbtx_android.ui.module.LoginActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/4/7.
 */
@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    Application application();

    UserComponent plus(UserModule userModule);

    LoginActivityComponent plus(LoginActivityModule module);
}
