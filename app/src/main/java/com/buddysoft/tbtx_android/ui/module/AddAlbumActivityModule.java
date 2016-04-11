package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.AlbumListActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IAddAlbumView;
import com.buddysoft.tbtx_android.ui.view.IAlbumListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/11.
 */
@Module
public class AddAlbumActivityModule {

    private IAddAlbumView mIAddAlbumView;

    public AddAlbumActivityModule(IAddAlbumView iAddAlbumView) {
        this.mIAddAlbumView = iAddAlbumView;
    }

    @Provides
    @ActivityScope
    IAddAlbumView provideAddAlbumActivity() {
        return mIAddAlbumView;
    }

    @Provides
    @ActivityScope
    AddAlbumActivityPresenter provideAlbumListActivityPresenter(RepositoriesManager repositoriesManager) {
        return new AddAlbumActivityPresenter(mIAddAlbumView, repositoriesManager);
    }
}
