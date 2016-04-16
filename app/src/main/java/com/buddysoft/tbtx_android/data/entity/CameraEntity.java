package com.buddysoft.tbtx_android.data.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/16.
 */
public class CameraEntity extends BaseEntity {


    /**
     * id : 1
     * serial : 550140727
     * cameraId : c0d9d4f248d9435d90ea7830ce49b6c3
     * name : 运动场
     * kgroupId : 1
     * kindergartenId : 1
     * classroomId : 0
     * deleted : 0
     * display : 0
     * accessToken : at.3i5afshndmie63l27bl773yg5751lhg6
     * createdAt : 2016-04-07 12:08:12
     * updatedAt : 2016-04-07 15:13:36
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String id;
        private String serial;
        private String cameraId;
        private String name;
        private String kgroupId;
        private String kindergartenId;
        private String classroomId;
        private String deleted;
        private String display;
        private String accessToken;
        private String createdAt;
        private String updatedAt;

        private Boolean isCheck = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getCameraId() {
            return cameraId;
        }

        public void setCameraId(String cameraId) {
            this.cameraId = cameraId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKgroupId() {
            return kgroupId;
        }

        public void setKgroupId(String kgroupId) {
            this.kgroupId = kgroupId;
        }

        public String getKindergartenId() {
            return kindergartenId;
        }

        public void setKindergartenId(String kindergartenId) {
            this.kindergartenId = kindergartenId;
        }

        public String getClassroomId() {
            return classroomId;
        }

        public void setClassroomId(String classroomId) {
            this.classroomId = classroomId;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getCheck() {
            return isCheck;
        }

        public void setCheck(Boolean check) {
            isCheck = check;
        }
    }
}
