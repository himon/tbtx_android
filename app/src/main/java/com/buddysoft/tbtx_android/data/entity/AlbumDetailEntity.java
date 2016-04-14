package com.buddysoft.tbtx_android.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lc on 16/4/12.
 */
public class AlbumDetailEntity extends BaseEntity {


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

    public static class ItemsEntity implements Parcelable {
        private String id;
        private String original;
        private String thumbnail;
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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.original);
            dest.writeString(this.thumbnail);
            dest.writeString(this.praiseCount);
            dest.writeString(this.commentCount);
            dest.writeString(this.deleted);
            dest.writeString(this.albumId);
            dest.writeString(this.createdAt);
        }

        public ItemsEntity() {
        }

        protected ItemsEntity(Parcel in) {
            this.id = in.readString();
            this.original = in.readString();
            this.thumbnail = in.readString();
            this.praiseCount = in.readString();
            this.commentCount = in.readString();
            this.deleted = in.readString();
            this.albumId = in.readString();
            this.createdAt = in.readString();
        }

        public static final Parcelable.Creator<ItemsEntity> CREATOR = new Parcelable.Creator<ItemsEntity>() {
            public ItemsEntity createFromParcel(Parcel source) {
                return new ItemsEntity(source);
            }

            public ItemsEntity[] newArray(int size) {
                return new ItemsEntity[size];
            }
        };
    }
}
