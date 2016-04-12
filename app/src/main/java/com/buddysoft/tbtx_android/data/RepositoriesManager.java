package com.buddysoft.tbtx_android.data;


import android.media.browse.MediaBrowser;

import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.data.api.LiveApi;
import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.EditAlbumEntity;
import com.buddysoft.tbtx_android.data.entity.UserEntity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/8.
 */
public class RepositoriesManager {

    private UserEntity mUser;
    private LiveApi mLiveApi;

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
        return mLiveApi.announcement(mUser.getObject().getKclassId(), mUser.getObject().getKindergartenId(), C.LimitType.LATEST)
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
}
