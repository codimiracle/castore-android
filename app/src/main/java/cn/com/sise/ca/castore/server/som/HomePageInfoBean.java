package cn.com.sise.ca.castore.server.som;

import java.util.List;

import cn.com.sise.ca.castore.components.BitmapCache;

public class HomePageInfoBean {
    public static final int POWERPOINT = 1;
    public static final int PAGE = 2;

    private int type;
    private List<AdvertisementInfoBean> advertisements;
    private String oddmentsTitle;

    public int getType() {
        return type;
    }

    public void setAdvertisements(List<AdvertisementInfoBean> advertisements) {
        this.advertisements = advertisements;
    }

    public List<AdvertisementInfoBean> getPowerpoints() {
        return advertisements;
    }

    public String getOddmentsTitle() {
        return oddmentsTitle;
    }

    public void setOddmentsTitle(String oddmentsTitle) {
        this.oddmentsTitle = oddmentsTitle;
    }

    public void setType(int type) {
        this.type = type;
    }
}
