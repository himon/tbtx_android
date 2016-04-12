package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AlbumEntity;
import com.buddysoft.tbtx_android.data.entity.EditAlbumEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IEditAlbumView;

/**
 * Created by lc on 16/4/12.
 */
public class EditAlbumActivityPresenter {

    private IEditAlbumView mIEditAlbumView;
    private RepositoriesManager mRepositoriesManager;

    public EditAlbumActivityPresenter(IEditAlbumView iEditAlbumView, RepositoriesManager repositoriesManager) {
        this.mIEditAlbumView = iEditAlbumView;
        mRepositoriesManager = repositoriesManager;
    }

    public void editAlbum(String albumId, String name, String cover) {
        mRepositoriesManager.editAlbum(albumId, name, cover).subscribe(new SimpleObserver<EditAlbumEntity>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(EditAlbumEntity editAlbumEntity) {
                mIEditAlbumView.setSaveSuccess(editAlbumEntity);
            }
        });
    }
}
