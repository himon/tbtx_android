package com.buddysoft.tbtx_android.data.entity;

/**
 * Created by lc on 16/4/13.
 */
public class EditAlbumEntity extends BaseEntity {

    /**
     * id : 1
     * name : 春游
     * cover : http://xx.xx.xx/xx.png
     * photoCount : 0
     * classId : 1
     * kindergartenId : 1
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
        private String name;
        private String cover;
        private String photoCount;
        private String classId;
        private String kindergartenId;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setPhotoCount(String photoCount) {
            this.photoCount = photoCount;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public void setKindergartenId(String kindergartenId) {
            this.kindergartenId = kindergartenId;
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

        public String getPhotoCount() {
            return photoCount;
        }

        public String getClassId() {
            return classId;
        }

        public String getKindergartenId() {
            return kindergartenId;
        }
    }
}
