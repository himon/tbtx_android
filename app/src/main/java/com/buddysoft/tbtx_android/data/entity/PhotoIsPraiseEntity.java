package com.buddysoft.tbtx_android.data.entity;

/**
 * Created by lc on 16/4/14.
 */
public class PhotoIsPraiseEntity extends BaseEntity {


    /**
     * isPraise : yes
     */

    private ObjectEntity object;

    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public ObjectEntity getObject() {
        return object;
    }

    public static class ObjectEntity {
        private String isPraise;

        public void setIsPraise(String isPraise) {
            this.isPraise = isPraise;
        }

        public String getIsPraise() {
            return isPraise;
        }
    }
}
