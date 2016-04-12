package com.buddysoft.tbtx_android.data.entity;

import java.util.List;

/**
 * Created by lc on 16/4/12.
 */
public class AlbumDetailEntity extends BaseEntity{


    /**
     * id : 2
     * original : http://xx.xx.xx
     * thumbnail : null
     * praiseCount : 0
     * commentCount : 0
     * deleted : 0
     * albumId : 1
     * createdAt : 2016-04-07 16:26:16
     */

    private List<ItemsEntity> items;

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public static class ItemsEntity {
        private String id;
        private String original;
        private Object thumbnail;
        private String praiseCount;
        private String commentCount;
        private String deleted;
        private String albumId;
        private String createdAt;

        public void setId(String id) {
            this.id = id;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public void setThumbnail(Object thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setPraiseCount(String praiseCount) {
            this.praiseCount = praiseCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getId() {
            return id;
        }

        public String getOriginal() {
            return original;
        }

        public Object getThumbnail() {
            return thumbnail;
        }

        public String getPraiseCount() {
            return praiseCount;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public String getDeleted() {
            return deleted;
        }

        public String getAlbumId() {
            return albumId;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
