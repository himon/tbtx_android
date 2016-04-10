package com.buddysoft.tbtx_android.ui.presenter;

import com.buddysoft.tbtx_android.data.RepositoriesManager;
import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.view.IHomeView;

import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public class HomeFragmentPresenter {

    private IHomeView mIHomeView;
    private RepositoriesManager mRepositoriesManager;

    public HomeFragmentPresenter(IHomeView iHomeView, RepositoriesManager repositoriesManager) {
        this.mIHomeView = iHomeView;
        this.mRepositoriesManager = repositoriesManager;
    }

    public void getAdList() {
        mRepositoriesManager.getAdList().subscribe(new SimpleObserver<AdvertisementEntity>(){
            @Override
            public void onNext(AdvertisementEntity advertisementEntity) {
                List<AdvertisementEntity.ItemsEntity> items = advertisementEntity.getItems();
                mIHomeView.setAdvertisement(items);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void getAnnouncementList() {
        mRepositoriesManager.getAnnouncementList().subscribe(new SimpleObserver<AnnouncementEntity>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(AnnouncementEntity announcementEntity) {
                List<AnnouncementEntity.ItemsEntity> items = announcementEntity.getItems();
                mIHomeView.setAnnouncement(items);
            }
        });
    }
}
