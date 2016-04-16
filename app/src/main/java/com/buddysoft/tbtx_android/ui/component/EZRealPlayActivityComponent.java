package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.activity.live.EZRealPlayActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.module.EZRealPlayActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2016/4/16.
 */
@ActivityScope
@Subcomponent(
        modules = EZRealPlayActivityModule.class
)
public interface EZRealPlayActivityComponent {

    EZRealPlayActivity inject(EZRealPlayActivity ezRealPlayActivity);
}
