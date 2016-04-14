package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumPhotoCommentEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoIsPraiseEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IAlbumPhotoDetailView;

/**
 * Created by lc on 16/4/14.
 */
public class AlbumPhotoDetailActivityPresenter {

    private IAlbumPhotoDetailView mIAlbumPhotoDetailView;
    private RepositoriesManager mRepositoriesManager;

    public AlbumPhotoDetailActivityPresenter(IAlbumPhotoDetailView iAlbumPhotoDetailView, RepositoriesManager repositoriesManager) {
        this.mIAlbumPhotoDetailView = iAlbumPhotoDetailView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void isPraise(String photoId) {
        mRepositoriesManager.isPraise(photoId).subscribe(new SimpleObserver<PhotoIsPraiseEntity>(){
            @Override
            public void onNext(PhotoIsPraiseEntity photoIsPraiseEntity) {
                mIAlbumPhotoDetailView.setIsPraise(photoIsPraiseEntity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void canclePraise(String photoId) {
        mRepositoriesManager.canclePraise(photoId).subscribe(new SimpleObserver<BaseEntity>(){
            @Override
            public void onNext(BaseEntity entity) {
                mIAlbumPhotoDetailView.setCanclePraise(entity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void praise(String photoId) {
        mRepositoriesManager.praise(photoId).subscribe(new SimpleObserver<BaseEntity>(){
            @Override
            public void onNext(BaseEntity entity) {
                mIAlbumPhotoDetailView.setPraise(entity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void commit(String photoId, String content, String commentId) {
        mRepositoriesManager.commitComment(photoId, content, commentId).subscribe(new SimpleObserver<AlbumPhotoCommentEntity>(){
            @Override
            public void onNext(AlbumPhotoCommentEntity albumPhotoCommentEntity) {
                super.onNext(albumPhotoCommentEntity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
