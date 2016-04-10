package com.buddysoft.tbtx_android.ui.module;


import com.buddysoft.tbtx_android.ui.presenter.LoginActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.ILoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/8.
 */
@Module
public class LoginActivityModule {
    private ILoginView mILoginView;

    public LoginActivityModule(ILoginView iLoginView) {
        this.mILoginView = iLoginView;
    }

    @Provides
    @ActivityScope
    ILoginView provideLoginActivity() {
        return mILoginView;
    }

    @Provides
    @ActivityScope
    LoginActivityPresenter providLoginActivityPresenter() {
        return new LoginActivityPresenter(mILoginView);
    }
}
