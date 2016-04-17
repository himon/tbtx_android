package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IPhotoShowView;

/**
 * Created by lc on 16/4/17.
 */
public class PhotoShowActivityPresenter {

    private IPhotoShowView mIPhotoShowView;
    private RepositoriesManager mRepositoriesManager;

    public PhotoShowActivityPresenter(IPhotoShowView iPhotoShowView, RepositoriesManager repositoriesManager) {
        this.mIPhotoShowView = iPhotoShowView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void delete(String photoId) {
        mRepositoriesManager.deletePhoto(photoId).subscribe(new SimpleObserver<BaseEntity>(){
            @Override
            public void onNext(BaseEntity entity) {
                super.onNext(entity);
                mIPhotoShowView.setDelSuccess(entity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
