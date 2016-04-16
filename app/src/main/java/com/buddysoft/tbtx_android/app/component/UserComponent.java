package com.buddysoft.tbtx_android.app.component;


import com.buddysoft.tbtx_android.app.module.UserModule;
import com.buddysoft.tbtx_android.app.scope.UserScope;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumDetailActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumListActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumPhotoDetailActivity;
import com.buddysoft.tbtx_android.ui.activity.album.EditAlbumActivity;
import com.buddysoft.tbtx_android.ui.component.AddAlbumActivityComponent;
import com.buddysoft.tbtx_android.ui.component.AlbumDetailActivityComponent;
import com.buddysoft.tbtx_android.ui.component.AlbumListActivityComponent;
import com.buddysoft.tbtx_android.ui.component.AlbumPhotoDetailActivityComponent;
import com.buddysoft.tbtx_android.ui.component.EZRealPlayActivityComponent;
import com.buddysoft.tbtx_android.ui.component.EditAlbumActivityComponent;
import com.buddysoft.tbtx_android.ui.component.MainActivityComponent;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumDetailActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumListActivityModule;
import com.buddysoft.tbtx_android.ui.module.AlbumPhotoDetailActivityModule;
import com.buddysoft.tbtx_android.ui.module.EZRealPlayActivityModule;
import com.buddysoft.tbtx_android.ui.module.EditAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.MainActivityModule;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2016/4/8.
 */
@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

        MainActivityComponent plus(MainActivityModule module);

        AlbumListActivityComponent plus(AlbumListActivityModule module);

        AddAlbumActivityComponent plus(AddAlbumActivityModule module);

        AlbumDetailActivityComponent plus(AlbumDetailActivityModule module);

        EditAlbumActivityComponent plus(EditAlbumActivityModule module);

        AlbumPhotoDetailActivityComponent plus(AlbumPhotoDetailActivityModule module);

        EZRealPlayActivityComponent plus(EZRealPlayActivityModule module);
}
