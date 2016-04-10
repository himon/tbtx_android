package com.buddysoft.tbtx_android.ui.module;


import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.MainActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IMainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/8.
 */
@Module
public class MainActivityModule {

    private IMainView mIMainView;

    public MainActivityModule(IMainView iMainView) {
        this.mIMainView = iMainView;
    }

    @Provides
    @ActivityScope
    IMainView provideMainActivity() {
        return mIMainView;
    }

    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(RepositoriesManager repositoriesManager) {
        return new MainActivityPresenter(mIMainView, repositoriesManager);
    }
}
