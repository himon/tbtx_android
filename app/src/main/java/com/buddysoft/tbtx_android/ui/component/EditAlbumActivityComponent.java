package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.activity.album.EditAlbumActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.EditAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/12.
 */
@ActivityScope
@Subcomponent(
        modules = EditAlbumActivityModule.class
)
public interface EditAlbumActivityComponent {

    EditAlbumActivity inject(EditAlbumActivity editAlbumActivity);
}