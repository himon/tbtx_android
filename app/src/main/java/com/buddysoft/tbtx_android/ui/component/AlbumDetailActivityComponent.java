package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumDetailActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumDetailActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/12.
 */
@ActivityScope
@Subcomponent(
        modules = AlbumDetailActivityModule.class
)
public interface AlbumDetailActivityComponent {

    AlbumDetailActivity inject(AlbumDetailActivity albumDetailActivity);
}
