package com.buddysoft.tbtx_android.data.event;

/**
 * Created by lc on 16/4/11.
 */
public class AlbumSearchEvent {

    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AlbumSearchEvent(String msg) {
        this.msg = msg;
    }
}
