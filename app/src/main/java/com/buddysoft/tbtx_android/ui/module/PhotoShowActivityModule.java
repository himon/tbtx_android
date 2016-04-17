package com.buddysoft.tbtx_android.ui.module;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.activity.album.PhotoShowActivity;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.presenter.PhotoShowActivityPresenter;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;
import com.buddysoft.tbtx_android.ui.view.IPhotoShowView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lc on 16/4/17.
 */
@Module
public class PhotoShowActivityModule {

    private IPhotoShowView mIPhotoShowView;

    public PhotoShowActivityModule(IPhotoShowView iPhotoShowView) {
        this.mIPhotoShowView = iPhotoShowView;
    }

    @Provides
    @ActivityScope
    IPhotoShowView providePhotoShowActivity() {
        return mIPhotoShowView;
    }

    @Provides
    @ActivityScope
    PhotoShowActivityPresenter providePhotoShowActivityPresenter(RepositoriesManager repositoriesManager) {
        return new PhotoShowActivityPresenter(mIPhotoShowView, repositoriesManager);
    }
}
