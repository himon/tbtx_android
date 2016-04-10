package com.buddysoft.tbtx_android.data.entity;

import java.util.List;

/**
 * Created by lc on 16/4/10.
 */
public class AnnouncementEntity extends BaseEntity {


    /**
     * id : 1
     * title : 旺斯达集团招生通知
     * summary : 市级文明幼儿园，曾经获得某某奖
     * type : 1
     * kgroupId : 3
     * kindergartenId : 15
     * classId : 15
     * createdAt : 2015-03-15 18:00:00
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
        private String title;
        private String summary;
        private String type;
        private String kgroupId;
        private String kindergartenId;
        private String classId;
        private String createdAt;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public String getType() {
            return type;
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
    }
}

