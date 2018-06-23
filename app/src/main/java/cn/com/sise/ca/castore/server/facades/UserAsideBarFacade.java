package cn.com.sise.ca.castore.server.facades;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.UserInfoBean;

public class UserAsideBarFacade implements Facade {
    private View view;

    private Bitmap avatar;
    private String nickname;
    private String email;

    private UserInfoBean userInfoBean;

    private ImageView viewAvatar;
    private TextView viewNickname;
    private TextView viewEmail;

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
        viewAvatar.setImageBitmap(avatar);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        viewNickname.setText(nickname);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        viewEmail.setText(email);
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        setNickname(userInfoBean.getNickname());
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        viewAvatar = view.findViewById(R.id.nav_user_avatar);
        viewNickname = view.findViewById(R.id.nav_user_name);
        viewEmail = view.findViewById(R.id.nav_user_email);
    }

    @Override
    public View getView() {
        return view;
    }
}
