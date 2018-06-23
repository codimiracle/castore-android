package cn.com.sise.ca.castore.server.som;

import java.sql.Timestamp;
import java.util.Date;

public class CommentInfoBean {
    /**
     * id : 10
     * targetId : 21
     * title : Hello
     * content : a good comment
     * contentId : 25
     * createdTime : 2018-05-13 13:02:40
     * userId : 1
     */

    private int id;
    private int targetId;
    private String title;
    private String content;
    private int contentId;
    private String createdTime;
    private int userId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}