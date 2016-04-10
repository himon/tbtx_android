package com.buddysoft.tbtx_android.ui.view;

import com.buddysoft.tbtx_android.data.entity.AlbumEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public interface IAlbumListView {
    void setAlbumList(ArrayList<AlbumEntity.ItemsEntity> items);
}
