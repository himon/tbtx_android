package com.buddysoft.tbtx_android.data.event;

/**
 * Created by lc on 16/4/17.
 */
public class AlbumPhotoEvent {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AlbumPhotoEvent(String msg) {
        this.msg = msg;
    }
}
