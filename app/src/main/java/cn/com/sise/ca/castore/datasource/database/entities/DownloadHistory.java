package cn.com.sise.ca.castore.datasource.database.entities;

import java.util.Date;

public class DownloadHistory implements Entity{
    private long id;
    private int appId;
    private Date downloadTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
}
