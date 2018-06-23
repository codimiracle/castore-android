package cn.com.sise.ca.castore.server.som;

import com.google.gson.annotations.SerializedName;

public class UserMessage {
    @SerializedName(value = "userData", alternate = {"userdata"})
    private UserInfoBean userInfoBean;

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }
}
