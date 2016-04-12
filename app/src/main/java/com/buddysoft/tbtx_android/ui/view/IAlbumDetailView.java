package com.buddysoft.tbtx_android.ui.view;

import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;

/**
 * Created by lc on 16/4/12.
 */
public interface IAlbumDetailView {
    void setDetail(AlbumDetailEntity albumDetailEntity);

    void setDel(BaseEntity entity);
}
