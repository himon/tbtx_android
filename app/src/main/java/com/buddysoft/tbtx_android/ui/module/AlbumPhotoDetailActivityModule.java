package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AlbumListActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.AlbumPhotoDetailActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IAlbumPhotoDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/14.
 */
@Module
public class AlbumPhotoDetailActivityModule {
    private IAlbumPhotoDetailView mIAlbumPhotoDetailView;

    public AlbumPhotoDetailActivityModule(IAlbumPhotoDetailView iAlbumPhotoDetailView) {
        this.mIAlbumPhotoDetailView = iAlbumPhotoDetailView;
    }

    @Provides
    @ActivityScope
    IAlbumPhotoDetailView provideAlbumPhotoDetailActivity() {
        return mIAlbumPhotoDetailView;
    }

    @Provides
    @ActivityScope
    AlbumPhotoDetailActivityPresenter provideAlbumPhotoDetailActivityPresenter(RepositoriesManager repositoriesManager) {
        return new AlbumPhotoDetailActivityPresenter(mIAlbumPhotoDetailView, repositoriesManager);
    }
}
