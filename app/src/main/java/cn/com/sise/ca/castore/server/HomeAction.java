package cn.com.sise.ca.castore.server;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.net.URL;

import cn.com.sise.ca.castore.server.som.HomePageMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class HomeAction {
    public static class AdvertisementAction implements ServerAction {
        public static final String HOME_PAGE_ADVERTISEMENT_QUERY = ServerUtils.BASE_QUERY_URL + "/Advertisement";

        private ServerActionCallback<HomePageMessage, Message> callback;

        public ServerActionCallback<HomePageMessage, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<HomePageMessage, Message> callback) {
            this.callback = callback;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(HOME_PAGE_ADVERTISEMENT_QUERY);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                HomePageMessage message = JSONUtils.fromJSON(response.getResponseBody(), HomePageMessage.class);
                if (message!= null) {
                    callback.onSuccess(message);
                } else {
                    callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
            } finally {
                httpClient.close();
            }

        }
    }

    public static class HomePageAction implements ServerAction {
        public static final String HOME_PAGE_QUERY = ServerUtils.BASE_QUERY_URL + "/Home/Pager/";
        private int pagerNumber = 1;

        private ServerActionCallback<HomePageMessage, Message> callback;

        public ServerActionCallback<HomePageMessage, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<HomePageMessage, Message> callback) {
            this.callback = callback;
        }


        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            this.pagerNumber = pagerNumber;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(HOME_PAGE_QUERY + pagerNumber);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                HomePageMessage homePageMessage = JSONUtils.fromJSON(response.getResponseBody(), HomePageMessage.class);
                if (homePageMessage != null) {
                    callback.onSuccess(homePageMessage);
                } else {
                    callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
            } finally {
                httpClient.close();
            }
        }
    }
}
