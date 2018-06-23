package cn.com.sise.ca.castore.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.UserAction;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.server.som.UserMessage;

public class UserProfileActivity extends ServerActionActivity {
    public static final String USER_PREFERENCE_ACTION = CAstoreApplication.PACKAGE_NAME + ".UserPreference";
    public static final String USER_PERSONAL_ACTION = CAstoreApplication.PACKAGE_NAME + ".UserPersonal";
    public static final String USER_FAVOURITE_ACTION = CAstoreApplication.PACKAGE_NAME + ".UserFavourite";

    private UserAction.UserProfileAction userProfileAction;
    private CAstoreApplication application;
    private UserMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        application = (CAstoreApplication) getApplication();
        refresh();
    }

    private void refresh() {
        if (application.isLogged() && application.getUserInfoBean() == null) {
            userProfileAction = new UserAction.UserProfileAction(this);
            userProfileAction.setCallback(new SimpleServerActionCallback<UserMessage>() {
                @Override
                public void onSuccess(UserMessage message) {
                    UserProfileActivity.this.message = message;
                    application.setUserInfoBean(UserProfileActivity.this.message.getUserInfoBean());
                }

                @Override
                public void onFailure(Message message) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                    builder.setTitle("个人信息");
                    builder.setMessage("获取个人信息失败，如果你的网络没有问题，请确认你已登录。");
                    builder.setPositiveButton("确定", null);
                    builder.show();
                }
            });
            addServerAction(userProfileAction);
        } else if (application.isLogged()) {
            Toast.makeText(this, application.getUserInfoBean().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
