package com.buddysoft.tbtx_android.ui.presenter;

import android.support.v7.widget.RecyclerView;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.CameraEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IEZRealPlayView;

/**
 * Created by Administrator on 2016/4/16.
 */
public class EZRealPlayActivityPresenter {

    private IEZRealPlayView mIEZRealPlayView;
    private RepositoriesManager mRepositoriesManager;

    public EZRealPlayActivityPresenter(IEZRealPlayView iezRealPlayView, RepositoriesManager repositoriesManager) {
        this.mIEZRealPlayView = iezRealPlayView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void getCameraList() {
        mRepositoriesManager.getCameraList().subscribe(new SimpleObserver<CameraEntity>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(CameraEntity cameraEntity) {
                super.onNext(cameraEntity);
            }
        });
    }
}
