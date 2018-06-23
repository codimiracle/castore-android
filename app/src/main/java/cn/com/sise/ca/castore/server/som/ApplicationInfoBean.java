package cn.com.sise.ca.castore.server.som;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class ApplicationInfoBean {
    /**
     * id : 10
     * contentId : 21
     * title : ICONApp
     * name : ICONApp
     * description : sfae.43
     * package : com.tencent.timm
     * platform : android-aarch32
     * developer : test
     * version : 2.3
     * createdTime : 2018-05-12 15:04:28
     * updatedTime : 2018-05-12 15:04:28
     */

    private int id;
    private int contentId;
    private String title;
    private String name;
    private String description;
    @SerializedName("package")
    private String packageName;
    private String platform;
    private String developer;
    private String version;
    private String createdTime;
    private String updatedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}