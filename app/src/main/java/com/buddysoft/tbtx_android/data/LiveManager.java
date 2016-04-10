package com.buddysoft.tbtx_android.data;


import com.buddysoft.tbtx_android.data.api.LiveApi;
import com.buddysoft.tbtx_android.data.entity.UserEntity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/7.
 */
public class LiveManager {

    private LiveApi liveApi;

    public LiveApi getLiveApi() {
        return liveApi;
    }

    public LiveManager(LiveApi liveApi) {
        this.liveApi = liveApi;
    }

    public Observable<UserEntity> login(String username, String mobile, String password) {
        return getLiveApi().login(username, mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
