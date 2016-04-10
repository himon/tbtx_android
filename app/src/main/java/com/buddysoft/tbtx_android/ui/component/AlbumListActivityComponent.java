package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.MainActivity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumListActivity;
import com.buddysoft.tbtx_android.ui.module.AlbumListActivityModule;
import com.buddysoft.tbtx_android.ui.module.MainActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/10.
 */
@ActivityScope
@Subcomponent(
        modules = AlbumListActivityModule.class
)
public interface AlbumListActivityComponent {

    AlbumListActivity inject(AlbumListActivity albumListActivity);
}
