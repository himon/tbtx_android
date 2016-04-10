package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.HomeFragmentPresenter;
import com.buddysoft.tbtx_android.ui.presenter.MainActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IHomeView;
import com.buddysoft.tbtx_android.ui.view.IMainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/10.
 */
@Module
public class HomeFragmentModule {

    private IHomeView mIHomeView;

    public HomeFragmentModule(IHomeView iHomeView) {
        this.mIHomeView = iHomeView;
    }

    @Provides
    @ActivityScope
    IHomeView provideHomeFragment() {
        return mIHomeView;
    }

    @Provides
    @ActivityScope
    HomeFragmentPresenter provideHomeFragmentPresenter(RepositoriesManager repositoriesManager) {
        return new HomeFragmentPresenter(mIHomeView, repositoriesManager);
    }
}
