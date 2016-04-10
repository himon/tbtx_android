package com.buddysoft.tbtx_android.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public class AlbumEntity extends BaseEntity {


    /**
     * id : 1
     * name : 春从春游
     * cover : http://xxx.xxx.cn/xxx.png
     * summary : 市级文明幼儿园，曾经获得某某奖
     * photoCount : 50
     * kgroupId : 3
     * kindergartenId : 15
     * classId : 1
     * createdAt : 2015-03-15 18:00:00
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
        private String name;
        private String cover;
        private String summary;
        private String photoCount;
        private String kgroupId;
        private String kindergartenId;
        private String classId;
        private String createdAt;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setPhotoCount(String photoCount) {
            this.photoCount = photoCount;
        }

        public void setKgroupId(String kgroupId) {
            this.kgroupId = kgroupId;
        }

        public void setKindergartenId(String kindergartenId) {
            this.kindergartenId = kindergartenId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCover() {
            return cover;
        }

        public String getSummary() {
            return summary;
        }

        public String getPhotoCount() {
            return photoCount;
        }

        public String getKgroupId() {
            return kgroupId;
        }

        public String getKindergartenId() {
            return kindergartenId;
        }

        public String getClassId() {
            return classId;
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
            dest.writeString(this.name);
            dest.writeString(this.cover);
            dest.writeString(this.summary);
            dest.writeString(this.photoCount);
            dest.writeString(this.kgroupId);
            dest.writeString(this.kindergartenId);
            dest.writeString(this.classId);
            dest.writeString(this.createdAt);
        }

        public ItemsEntity() {
        }

        protected ItemsEntity(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.cover = in.readString();
            this.summary = in.readString();
            this.photoCount = in.readString();
            this.kgroupId = in.readString();
            this.kindergartenId = in.readString();
            this.classId = in.readString();
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
