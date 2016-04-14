package com.buddysoft.tbtx_android.ui.component;

import com.buddysoft.tbtx_android.ui.activity.MainActivity;
import com.buddysoft.tbtx_android.ui.fragment.HomeFragment;
import com.buddysoft.tbtx_android.ui.module.HomeFragmentModule;
import com.buddysoft.tbtx_android.ui.module.MainActivityModule;
import com.buddysoft.tbtx_android.ui.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by lc on 16/4/10.
 */
@ActivityScope
@Subcomponent(
        modules = HomeFragmentModule.class
)
public interface HomeFragmentComponent {

    HomeFragment inject(HomeFragment homeFragment);
}
