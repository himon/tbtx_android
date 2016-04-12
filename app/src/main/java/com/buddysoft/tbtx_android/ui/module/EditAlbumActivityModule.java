package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.EditAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IEditAlbumView;
import com.buddysoft.tbtx_android.ui.view.IEditAlbumView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/12.
 */
@Module
public class EditAlbumActivityModule {

    private IEditAlbumView mIEditAlbumView;

    public EditAlbumActivityModule(IEditAlbumView iEditAlbumView) {
        this.mIEditAlbumView = iEditAlbumView;
    }

    @Provides
    @ActivityScope
    IEditAlbumView provideEditAlbumActivity() {
        return mIEditAlbumView;
    }

    @Provides
    @ActivityScope
    EditAlbumActivityPresenter provideEditAlbumActivityPresenter(RepositoriesManager repositoriesManager) {
        return new EditAlbumActivityPresenter(mIEditAlbumView, repositoriesManager);
    }
}
