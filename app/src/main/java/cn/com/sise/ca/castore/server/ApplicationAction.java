package cn.com.sise.ca.castore.server;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import cn.com.sise.ca.castore.server.som.ApplicationMessage;
import cn.com.sise.ca.castore.server.som.ApplicationQueryMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class ApplicationAction {
    public static class DetailsAction implements ServerAction {
        public static final String APPLICATION_DETAILS_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/";
        private int applicationId;
        private String applicationPackage;
        private ServerActionCallback<ApplicationMessage, Message> callback;

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public String getApplicationPackage() {
            return applicationPackage;
        }

        public ServerActionCallback<ApplicationMessage, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<ApplicationMessage, Message> callback) {
            this.callback = callback;
        }

        public void setApplicationPackage(String applicationPackage) {
            this.applicationPackage = applicationPackage;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url;
                if (applicationId > 0) {
                    url = new URL(APPLICATION_DETAILS_QUERY + applicationId);
                } else {
                    url = new URL(APPLICATION_DETAILS_QUERY + applicationPackage);
                }
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                ApplicationMessage applicationMessage = JSONUtils.fromJSON(response.getResponseBody(), ApplicationMessage.class);
                if (applicationMessage != null) {
                    callback.onSuccess(applicationMessage);
                } else {
                    callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
            }
        }
    }

    public static class SearchAction implements ServerAction {
        public static final String APPLICATION_SEARCH_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/Search/Pager/%d&kw=%s";
        private int pagerNumber = 1;
        private String keyword;

        private ServerActionCallback<ApplicationQueryMessage, Message> callback;

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            if (pagerNumber < 1) {
                pagerNumber = 1;
            }
            this.pagerNumber = pagerNumber;
        }

        public ServerActionCallback<ApplicationQueryMessage, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<ApplicationQueryMessage, Message> callback) {
            this.callback = callback;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        @Override
        public void execute() {
            String realURL = String.format(Locale.CANADA, APPLICATION_SEARCH_QUERY, pagerNumber, URLEncoder.encode(keyword));
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(realURL);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                ApplicationQueryMessage message = JSONUtils.fromJSON(response.getResponseBody(), ApplicationQueryMessage.class);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                    callback.onSuccess(message);
                } else {
                    callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
            }
        }
    }

    public static class ListAction implements ServerAction {

        public static final String APPLICATION_LIST_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/Pager/%d";

        private int pagerNumber;
        private ServerActionCallback<ApplicationQueryMessage, Message> callback;

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            this.pagerNumber = pagerNumber;
        }

        public ServerActionCallback<ApplicationQueryMessage, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<ApplicationQueryMessage, Message> callback) {
            this.callback = callback;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();

        }
    }
}
