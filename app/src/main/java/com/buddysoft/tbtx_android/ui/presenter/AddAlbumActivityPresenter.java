package com.buddysoft.tbtx_android.ui.presenter;

import android.text.TextUtils;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;
import com.buddysoft.tbtx_android.ui.view.IAddAlbumView;
import com.github.lazylibrary.util.ToastUtils;

/**
 * Created by lc on 16/4/11.
 */
public class AddAlbumActivityPresenter {

    private IAddAlbumView mIAddAlbumView;
    private RepositoriesManager mRepositoriesManager;

    public AddAlbumActivityPresenter(IAddAlbumView iAddAlbumView, RepositoriesManager repositoriesManager) {
        this.mIAddAlbumView = iAddAlbumView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void createAlbum(String name, String cover) {

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast((AddAlbumActivity) mIAddAlbumView, "班级名称不能为空");
            return;
        }

        mRepositoriesManager.createAlbum(name, cover).subscribe(new SimpleObserver<AlbumEntity>() {
            @Override
            public void onNext(AlbumEntity albumEntity) {
                mIAddAlbumView.setCreateSuccess(albumEntity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
