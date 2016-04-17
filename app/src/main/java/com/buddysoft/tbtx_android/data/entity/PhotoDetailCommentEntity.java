package com.buddysoft.tbtx_android.data.entity;

import java.util.List;

/**
 * Created by lc on 16/4/17.
 */
public class PhotoDetailCommentEntity extends BaseEntity {


    /**
     * id : 5
     * photoId : 1
     * operatorId : 1
     * content : gaga
     * commentId : 0
     * createdAt : 2016-04-13 09:19:22
     * publisher : {"name":"","relation":"0","avatar":null}
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
        private String photoId;
        private String operatorId;
        private String content;
        private String commentId;
        private String createdAt;
        /**
         * name :
         * relation : 0
         * avatar : null
         */

        private PublisherEntity publisher;

        public void setId(String id) {
            this.id = id;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public void setOperatorId(String operatorId) {
            this.operatorId = operatorId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setPublisher(PublisherEntity publisher) {
            this.publisher = publisher;
        }

        public String getId() {
            return id;
        }

        public String getPhotoId() {
            return photoId;
        }

        public String getOperatorId() {
            return operatorId;
        }

        public String getContent() {
            return content;
        }

        public String getCommentId() {
            return commentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public PublisherEntity getPublisher() {
            return publisher;
        }

        public static class PublisherEntity {
            private String name;
            private String relation;
            private String avatar;

            public void setName(String name) {
                this.name = name;
            }

            public void setRelation(String relation) {
                this.relation = relation;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public String getRelation() {
                return relation;
            }

            public String getAvatar() {
                return avatar;
            }
        }
    }
}
