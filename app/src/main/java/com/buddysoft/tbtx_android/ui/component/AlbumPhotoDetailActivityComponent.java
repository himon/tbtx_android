package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.AlbumListActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumPhotoDetailActivity;
import com.buddysoft.tbtx_android.ui.module.AlbumListActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumPhotoDetailActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by lc on 16/4/14.
 */
@ActivityScope
@Subcomponent(
        modules = AlbumPhotoDetailActivityModule.class
)
public interface AlbumPhotoDetailActivityComponent {
    AlbumPhotoDetailActivity inject(AlbumPhotoDetailActivity albumPhotoDetailActivity);
}
