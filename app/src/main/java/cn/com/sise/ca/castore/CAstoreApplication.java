package cn.com.sise.ca.castore;

import android.app.Application;
import android.content.Intent;

import cn.com.sise.ca.castore.server.som.UserInfoBean;

public class CAstoreApplication extends Application {
    public static final String PACKAGE_NAME;
    static {
        PACKAGE_NAME = CAstoreApplication.class.getPackage().getName();
    }
    private boolean logged;
    private UserInfoBean userInfoBean;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
        Intent intent = new Intent(getPackageName() + (logged ? ".USER_LOG_IN" : ".USER_LOG_OUT"));
        sendBroadcast(intent);
    }

    public boolean isLogged() {
        return logged;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        Intent intent = new Intent(getPackageName() + "USER_INFO_UPDATE");
        sendBroadcast(intent);
    }
}
