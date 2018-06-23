package cn.com.sise.ca.castore.server;

import android.app.Activity;

import com.codimiracle.libs.lumehttp.FormData;
import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpRequest;
import com.codimiracle.libs.lumehttp.HttpResponse;
import com.codimiracle.libs.lumehttp.URLEncodedFormData;

import java.io.IOException;
import java.net.URL;

import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.server.som.UserMessage;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

/**
 * Not threading safety.
 * the Action just considering for the single thread.
 */
public class UserAction {
    public static class SignInAction extends CallbackRunOnUiServerAction<Message> {
        public static final String SIGN_IN_QUERY = ServerUtils.BASE_QUERY_URL + "/User/SignIn";
        private static final String SIGN_IN_ACTION = "user_sign_in";

        private String username;
        private String password;

        public SignInAction(Activity activity) {
            super(activity);
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        @Override
        protected Message handle() throws IOException {
            HttpClient client = getHttpClient();
            URL url = new URL(SIGN_IN_QUERY);
            client.open(url);
            HttpRequest request = client.getHttpRequest();
            FormData data = new URLEncodedFormData();
            data.put("username", username);
            data.put("password", password);
            data.put(SIGN_IN_ACTION, SIGN_IN_ACTION);
            request.setFormData(data);
            HttpResponse response = client.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), Message.class);
        }
    }

    public static class SignOutAction extends CallbackRunOnUiServerAction<Message> {
        public static final String SIGN_OUT_QUERY = ServerUtils.BASE_QUERY_URL + "/User/SignOut";

        public SignOutAction(Activity activity) {
            super(activity);
        }

        @Override
        protected Message handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url = new URL(SIGN_OUT_QUERY);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), Message.class);
        }
    }

    public static class SignUpActionCallback extends CallbackRunOnUiServerAction<Message> {
        public static final String SIGN_UP_QUERY = ServerUtils.BASE_QUERY_URL + "/User/SignUp";

        private String username, password, introduction, nickname;
        private int gender;
        private boolean license;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public boolean isLicense() {
            return license;
        }

        public void setLicense(boolean license) {
            this.license = license;
        }

        public SignUpActionCallback(Activity activity) {
            super(activity);
        }

        @Override
        protected Message handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url = new URL(SIGN_UP_QUERY);
            httpClient.open(url);

            FormData formData = new URLEncodedFormData();
            formData.put("username", username);
            formData.put("password", password);
            formData.put("nickname", nickname);
            formData.put("gender", "" + gender);
            formData.put("description", introduction);
            formData.put("license", license ? "on" : "off");
            formData.put("user_sign_up", "user_sign_up");

            HttpRequest request = httpClient.getHttpRequest();
            request.setFormData(formData);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), Message.class);
        }
    }

    public static class UserProfileAction extends CallbackRunOnUiServerAction<UserMessage> {

        public static final String WHO_IS_ME_QUERY = ServerUtils.BASE_QUERY_URL + "/User";

        public UserProfileAction(Activity activity) {
            super(activity);
        }

        @Override
        protected UserMessage handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url = new URL(WHO_IS_ME_QUERY);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), UserMessage.class);
        }
    }
}
