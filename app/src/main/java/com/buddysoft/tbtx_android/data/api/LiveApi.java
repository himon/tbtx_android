package com.buddysoft.tbtx_android.data.api;


import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
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
    Observable<AnnouncementEntity> announcement(@Field("classId") String classId, @Field("kindergartenId") String kindergartenId, @Field("limit") String limit);

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

}
