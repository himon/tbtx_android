package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AlbumListActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IAlbumListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/10.
 */
@Module
public class AlbumListActivityModule {

    private IAlbumListView mIAlbumListView;

    public AlbumListActivityModule(IAlbumListView iAlbumListView) {
        this.mIAlbumListView = iAlbumListView;
    }

    @Provides
    @ActivityScope
    IAlbumListView providealbumListActivity() {
        return mIAlbumListView;
    }

    @Provides
    @ActivityScope
    AlbumListActivityPresenter provideAlbumListActivityPresenter(RepositoriesManager repositoriesManager) {
        return new AlbumListActivityPresenter(mIAlbumListView, repositoriesManager);
    }
}
