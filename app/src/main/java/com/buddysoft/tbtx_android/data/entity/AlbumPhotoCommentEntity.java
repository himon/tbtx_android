package com.buddysoft.tbtx_android.data.entity;

/**
 * Created by lc on 16/4/14.
 */
public class AlbumPhotoCommentEntity extends BaseEntity {


    /**
     * operatorId : 1
     * content : gaga
     * photoId : 1
     * id : 5
     */

    private ObjectEntity object;

    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public ObjectEntity getObject() {
        return object;
    }

    public static class ObjectEntity {
        private String operatorId;
        private String content;
        private String photoId;
        private int id;

        public void setOperatorId(String operatorId) {
            this.operatorId = operatorId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOperatorId() {
            return operatorId;
        }

        public String getContent() {
            return content;
        }

        public String getPhotoId() {
            return photoId;
        }

        public int getId() {
            return id;
        }
    }
}
