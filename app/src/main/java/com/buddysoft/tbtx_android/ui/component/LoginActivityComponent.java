package com.buddysoft.tbtx_android.ui.component;


import com.buddysoft.tbtx_android.ui.activity.account.LoginActivity;
import com.buddysoft.tbtx_android.ui.module.LoginActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2016/4/8.
 */
@ActivityScope
@Subcomponent(
        modules = LoginActivityModule.class
)
public interface LoginActivityComponent {

    LoginActivity inject(LoginActivity loginActivity);
}
