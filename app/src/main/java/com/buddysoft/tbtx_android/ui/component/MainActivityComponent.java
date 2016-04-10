package com.buddysoft.tbtx_android.ui.component;


import com.buddysoft.tbtx_android.ui.activity.MainActivity;
import com.buddysoft.tbtx_android.ui.fragment.HomeFragment;
import com.buddysoft.tbtx_android.ui.module.HomeFragmentModule;
import com.buddysoft.tbtx_android.ui.module.MainActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by Administrator on 2016/4/8.
 */
@ActivityScope
@Subcomponent(
        modules = MainActivityModule.class
)
public interface MainActivityComponent {

    MainActivity inject(MainActivity mainActivity);

    HomeFragmentComponent plus(HomeFragmentModule module);
}
