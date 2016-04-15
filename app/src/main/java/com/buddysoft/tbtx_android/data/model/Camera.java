package com.buddysoft.tbtx_android.data.model;


import java.util.List;

/**
 * 摄像头
 */
public class Camera {
    private String id;
    private String serial;
    private String cameraId;
    private String name;
    private String kgroupId;
    private String kindergartenId;
    private int classroomId;
    private String deleted;
    private String display;
    private String accessToken;
    private String createdAt;
    private String updatedAt;

    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    private Boolean isCheck = false;


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

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

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
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

//    public static Camera fromJSON(String JSONString) {
//        if (JSONString == null)
//            return null;
//        return JsonUtils.fromJson(JSONString, Camera.class);
//    }
//
//    public static List<Camera> fromJSONS(String JSONSString) {
//        if (JSONSString == null)
//            return null;
//        return JsonUtils.fromJsons(JSONSString, Camera.class);
//    }

}
