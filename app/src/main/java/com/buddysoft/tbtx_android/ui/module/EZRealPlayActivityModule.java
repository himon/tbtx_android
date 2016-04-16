package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.EZRealPlayActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IEZRealPlayView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/16.
 */
@Module
public class EZRealPlayActivityModule {

    private IEZRealPlayView mIEZRealPlayView;

    public EZRealPlayActivityModule(IEZRealPlayView iezRealPlayView) {
        this.mIEZRealPlayView = iezRealPlayView;
    }

    @Provides
    @ActivityScope
    IEZRealPlayView provideEZRealPlayActivity() {
        return mIEZRealPlayView;
    }

    @Provides
    @ActivityScope
    EZRealPlayActivityPresenter provideEZRealPlayActivityPresenter(RepositoriesManager repositoriesManager) {
        return new EZRealPlayActivityPresenter(mIEZRealPlayView, repositoriesManager);
    }
}
