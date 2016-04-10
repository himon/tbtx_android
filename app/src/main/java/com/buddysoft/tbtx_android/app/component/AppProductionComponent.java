package com.buddysoft.tbtx_android.app.component;

import com.buddysoft.tbtx_android.app.module.ApiProducerModule;
import com.buddysoft.tbtx_android.app.module.UserModule;
import com.buddysoft.tbtx_android.data.LiveManager;
import com.google.common.util.concurrent.ListenableFuture;

import dagger.producers.ProductionComponent;

/**
 * Created by Administrator on 2016/4/7.
 */
@ProductionComponent(
        dependencies = AppComponent.class,
        modules = ApiProducerModule.class
)
public interface AppProductionComponent {

    ListenableFuture<LiveManager> liveManager();

    ListenableFuture<UserModule.Factory> userModuleFactory();
}
