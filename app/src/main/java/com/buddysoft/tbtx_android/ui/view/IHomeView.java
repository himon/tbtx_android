package com.buddysoft.tbtx_android.ui.view;

import com.buddysoft.tbtx_android.data.entity.AdvertisementEntity;
import com.buddysoft.tbtx_android.data.entity.AnnouncementEntity;

import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public interface IHomeView {
    void setAdvertisement(List<AdvertisementEntity.ItemsEntity> items);

    void setAnnouncement(List<AnnouncementEntity.ItemsEntity> items);
}
