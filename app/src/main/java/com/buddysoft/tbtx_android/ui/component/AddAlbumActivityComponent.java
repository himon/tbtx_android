package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumListActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumListActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/11.
 */
@ActivityScope
@Subcomponent(
        modules = AddAlbumActivityModule.class
)
public interface AddAlbumActivityComponent {

    AddAlbumActivity inject(AddAlbumActivity addAlbumActivity);
}
