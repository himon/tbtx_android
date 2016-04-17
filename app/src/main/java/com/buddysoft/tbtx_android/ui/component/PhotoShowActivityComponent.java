package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.PhotoShowActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.PhotoShowActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/17.
 */
@ActivityScope
@Subcomponent(
        modules = PhotoShowActivityModule.class
)
public interface PhotoShowActivityComponent {

    PhotoShowActivity inject(PhotoShowActivity photoShowActivity);
}
