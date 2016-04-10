package com.buddysoft.tbtx_android.data.entity;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BaseEntity {

    /**
     * status : 0
     * msg : 获取数据成功！
     */

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
