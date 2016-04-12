package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IAlbumDetailView;

/**
 * Created by lc on 16/4/12.
 */
public class AlbumDetailActivityPresenter {

    private IAlbumDetailView mIAlbumDetailView;
    private RepositoriesManager mRepositoriesManager;

    public AlbumDetailActivityPresenter(IAlbumDetailView iAlbumDetailView, RepositoriesManager repositoriesManager) {
        this.mIAlbumDetailView = iAlbumDetailView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void getAlbumPhoto(String albumId) {
        mRepositoriesManager.getAlbumPhoto(albumId).subscribe(new SimpleObserver<AlbumDetailEntity>(){
            @Override
            public void onNext(AlbumDetailEntity albumDetailEntity) {
                mIAlbumDetailView.setDetail(albumDetailEntity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    public void delAlbum(String albumId) {
        mRepositoriesManager.delAlbum(albumId).subscribe(new SimpleObserver<BaseEntity>(){
            @Override
            public void onNext(BaseEntity entity) {
                mIAlbumDetailView.setDel(entity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
