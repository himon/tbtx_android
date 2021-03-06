package com.buddysoft.tbtx_android.data.entity;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UserEntity extends BaseEntity {


    /**
     * id : 1
     * username : liuwanwei
     * password : 123456
     * avatar : null
     * kgroupId : 0
     * kindergartenId : 0
     * kclassId : 0
     * type : -1
     * mobileRole : -1
     * mobile : null
     * deleted : 0
     * createdAt : 2016-04-05 17:43:20
     * updatedAt : 2016-04-05 17:43:20
     */

    private ObjectEntity object;


    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public ObjectEntity getObject() {
        return object;
    }

    public static class ObjectEntity {
        private String id;
        private String username;
        private String password;
        private String avatar;
        private String kgroupId;
        private String kindergartenId;
        private String kclassId;
        private String type;
        private int mobileRole;
        private String mobile;
        private String deleted;
        private String createdAt;
        private String updatedAt;

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public void setKgroupId(String kgroupId) {
            this.kgroupId = kgroupId;
        }

        public void setKindergartenId(String kindergartenId) {
            this.kindergartenId = kindergartenId;
        }

        public void setKclassId(String kclassId) {
            this.kclassId = kclassId;
        }

        public void setType(String type) {
            this.type = type;
        }


        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }


        public String getKgroupId() {
            return kgroupId;
        }

        public String getKindergartenId() {
            return kindergartenId;
        }

        public String getKclassId() {
            return kclassId;
        }

        public String getType() {
            return type;
        }


        public String getDeleted() {
            return deleted;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getMobileRole() {
            return mobileRole;
        }

        public void setMobileRole(int mobileRole) {
            this.mobileRole = mobileRole;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
