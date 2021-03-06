package com.buddysoft.tbtx_android.data.api;


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

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface LiveApi {

    @FormUrlEncoded
    @POST("kuser/login")
    Observable<UserEntity> login(@Field("username") String username, @Field("mobile") String mobile, @Field("password") String password);

    /**
     * 查询广告
     *
     * @param kindergartenId
     * @return
     */
    @FormUrlEncoded
    @POST("advertisement/index")
    Observable<AdvertisementEntity> advertisement(@Field("kindergartenId") String kindergartenId);

    /**
     * 查询公告
     *
     * @param classId
     * @param kindergartenId
     * @param limit
     * @return
     */
    @FormUrlEncoded
    @POST("announcement/index")
    Observable<AnnouncementEntity> announcement(@Field("classId") String classId, @Field("limit") String limit);

    /**
     * 查询相册
     *
     * @param kindergartenId
     * @param classId
     * @param createdAt
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("album/index")
    Observable<AlbumEntity> album(@Field("kindergartenId") String kindergartenId, @Field("classId") String classId, @Field("createdAt") String createdAt, @Field("name") String name);

    /**
     * 创建相册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("album/create")
    Observable<AlbumEntity> createAlbum(@Field("kindergartenId") String kindergartenId, @Field("classId") String classId, @Field("name") String name, @Field("cover") String cover, @Field("operatorId") String operatorId);

    /**
     * 获取相册相片
     *
     * @param albumId
     * @return
     */
    @FormUrlEncoded
    @POST("album-photo/index")
    Observable<AlbumDetailEntity> getAlbumPhoto(@Field("albumId") String albumId);

    /**
     * 修改相册
     *
     * @param albumId
     * @param name
     * @param cover
     * @param operatorId
     * @return
     */
    @FormUrlEncoded
    @POST("album/update")
    Observable<EditAlbumEntity> editAlbum(@Field("albumId") String albumId, @Field("name") String name, @Field("cover") String cover, @Field("operatorId") String operatorId);

    /**
     * 删除相册
     *
     * @param albumId
     * @return
     */
    @FormUrlEncoded
    @POST("album/delete")
    Observable<BaseEntity> delAlbum(@Field("albumId") String albumId, @Field("operatorId") String operatorId);

    @FormUrlEncoded
    @POST("album-photo/add")
    Observable<BaseEntity> uploadPhoto(@Field("kuserId") String kuserId, @Field("albumId") String albumId, @Field("photos") String photos);

    @FormUrlEncoded
    @POST("album-photo/is-praise")
    Observable<PhotoIsPraiseEntity> isPraise(@Field("operatorId") String operatorId, @Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("album-photo/praise")
    Observable<BaseEntity> praise(@Field("operatorId") String operatorId, @Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("album-photo/cancel-praise")
    Observable<BaseEntity> canclePraise(@Field("operatorId") String operatorId, @Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("album-photo/comment")
    Observable<AlbumPhotoCommentEntity> commitComment(@Field("photoId") String photoId, @Field("operatorId") String operatorId, @Field("content") String content);

    @FormUrlEncoded
    @POST("camera/index")
    Observable<CameraEntity> getCameraList(@Field("kuserId") String kuserId);

    @FormUrlEncoded
    @POST("album-photo/delete")
    Observable<BaseEntity> deletePhoto(@Field("kuserId") String kuserId, @Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("album-photo/comment-list")
    Observable<PhotoDetailCommentEntity> getCommentList(@Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("album-photo/delete-comment")
    Observable<BaseEntity> delComment(@Field("commentId") String commentId, @Field("operatorId") String operatorId);
}
