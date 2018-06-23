package cn.com.sise.ca.castore.server.som;

import java.sql.Timestamp;
import java.util.Date;

public class ResourceInfoBean {
    /**
     * id : 18
     * targetId : 21
     * field : app_powerpoint
     * path : static/files/9bd565a63224a7c944fc566692d03e69.jpg
     * size : 164984
     * mimeType : image/jpeg
     * uploadedTime : 2018-05-12 15:04:28
     * updatedTime : 2018-05-12 15:04:28
     */

    private int id;
    private int targetId;
    private String field;
    private String path;
    private String size;
    private String mimeType;
    private String uploadedTime;
    private String updatedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}