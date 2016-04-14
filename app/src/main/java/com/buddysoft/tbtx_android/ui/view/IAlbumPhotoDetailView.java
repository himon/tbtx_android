package com.buddysoft.tbtx_android.ui.view;

import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoIsPraiseEntity;

/**
 * Created by lc on 16/4/14.
 */
public interface IAlbumPhotoDetailView {
    void setIsPraise(PhotoIsPraiseEntity photoIsPraiseEntity);

    void setCanclePraise(BaseEntity entity);

    void setPraise(BaseEntity entity);
}
