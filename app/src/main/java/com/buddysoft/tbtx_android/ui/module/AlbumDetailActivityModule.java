package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.AlbumDetailActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IAddAlbumView;
import com.buddysoft.tbtx_android.ui.view.IAlbumDetailView;
import com.buddysoft.tbtx_android.ui.view.IAlbumListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/12.
 */
@Module
public class AlbumDetailActivityModule {

    private IAlbumDetailView mIAlbumDetailView;

    public AlbumDetailActivityModule(IAlbumDetailView iAlbumDetailView) {
        this.mIAlbumDetailView = iAlbumDetailView;
    }

    @Provides
    @ActivityScope
    IAlbumDetailView provideAlbumDetailActivity() {
        return mIAlbumDetailView;
    }

    @Provides
    @ActivityScope
    AlbumDetailActivityPresenter provideAlbumDetailActivityPresenter(RepositoriesManager repositoriesManager) {
        return new AlbumDetailActivityPresenter(mIAlbumDetailView, repositoriesManager);
    }
}
