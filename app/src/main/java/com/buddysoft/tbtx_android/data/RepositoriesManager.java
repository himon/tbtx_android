package com.buddysoft.tbtx_android.data;


import android.media.browse.MediaBrowser;

import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.data.api.LiveApi;
import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumPhotoCommentEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.CameraEntity;
import com.buddysoft.tbtx_android.data.entity.EditAlbumEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoDetailCommentEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoIsPraiseEntity;
import com.buddysoft.tbtx_android.data.entity.UserEntity;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/8.
 */
public class RepositoriesManager {

    private UserEntity mUser;
    private LiveApi mLiveApi;

    public UserEntity getUser() {
        return mUser;
    }

    public RepositoriesManager(UserEntity user, LiveApi liveApi) {
        this.mUser = user;
        this.mLiveApi = liveApi;
    }

    public Observable<AdvertisementEntity> getAdList() {
        return mLiveApi.advertisement(mUser.getObject().getKindergartenId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<AnnouncementEntity> getAnnouncementList() {

//        String classId = "";
//        String kindergartenId = "";
//
//        switch (mUser.getObject().getMobileRole()) {
//            case C.Role.RoleFirstParents:
//            case C.Role.RoleOtherParents:
//            case C.Role.RoleFirstTeacher:
//            case C.Role.RoleSecondTeacher:
//                classId = mUser.getObject().getKclassId();
//                break;
//            case C.Role.RoleKindergartenLeader:
//            case C.Role.RoleNurse:
//            case C.Role.RoleAdministrationTeacher:
//            case C.Role.RoleOtherTeacher:
//                kindergartenId = mUser.getObject().getKindergartenId();
//                break;
//        }
        return mLiveApi.announcement("0", C.LimitType.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<AlbumEntity> album(String createdAt, String name) {
        return mLiveApi.album(mUser.getObject().getKindergartenId(), mUser.getObject().getKclassId(), createdAt, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AlbumEntity> createAlbum(String name, String cover) {
        return mLiveApi.createAlbum(mUser.getObject().getKindergartenId(), mUser.getObject().getKclassId(), name, cover, mUser.getObject().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AlbumDetailEntity> getAlbumPhoto(String albumId) {
        return mLiveApi.getAlbumPhoto(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<EditAlbumEntity> editAlbum(String albumId, String name, String cover) {
        return mLiveApi.editAlbum(albumId, name, cover, mUser.getObject().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> delAlbum(String albumId) {
        return mLiveApi.delAlbum(albumId, mUser.getObject().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> uploadPhoto(String albumId, String photos) {
        return mLiveApi.uploadPhoto(mUser.getObject().getId(), albumId, photos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PhotoIsPraiseEntity> isPraise(String photoId) {
        return mLiveApi.isPraise(mUser.getObject().getId(), photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> canclePraise(String photoId) {
        return mLiveApi.canclePraise(mUser.getObject().getId(), photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> praise(String photoId) {
        return mLiveApi.praise(mUser.getObject().getId(), photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AlbumPhotoCommentEntity> commitComment(String photoId, String content, String commentId) {
        return mLiveApi.commitComment(photoId, mUser.getObject().getId(), content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CameraEntity> getCameraList() {
        return mLiveApi.getCameraList(mUser.getObject().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> deletePhoto(String photoId) {
        return mLiveApi.deletePhoto(mUser.getObject().getId(), photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PhotoDetailCommentEntity> getCommentList(String photoId) {
        return mLiveApi.getCommentList(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseEntity> delComment(String commentId) {
        return mLiveApi.delComment(commentId, mUser.getObject().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
