package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IAddAlbumView;

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

        mRepositoriesManager.createAlbum(name, cover).subscribe(new SimpleObserver<AlbumEntity>(){
            @Override
            public void onNext(AlbumEntity albumEntity) {
                super.onNext(albumEntity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
