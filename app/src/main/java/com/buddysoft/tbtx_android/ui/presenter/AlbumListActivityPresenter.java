package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IAlbumListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public class AlbumListActivityPresenter {

    private IAlbumListView mIAlbumListView;
    private RepositoriesManager mRepositoriesManager;

    public AlbumListActivityPresenter(IAlbumListView iAlbumListView, RepositoriesManager repositoriesManager) {
        this.mIAlbumListView = iAlbumListView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void getAlbumList(String createdAt, String name) {
        mRepositoriesManager.album(createdAt, name).subscribe(new SimpleObserver<AlbumEntity>(){
            @Override
            public void onNext(AlbumEntity albumEntity) {
                if(albumEntity.getStatus() == 0){
                    List<AlbumEntity.ItemsEntity> items = albumEntity.getItems();
                    mIAlbumListView.setAlbumList((ArrayList<AlbumEntity.ItemsEntity>) items);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
