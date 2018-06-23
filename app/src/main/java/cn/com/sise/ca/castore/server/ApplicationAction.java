package cn.com.sise.ca.castore.server;

import android.app.Activity;

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
    public static class DetailsAction extends CallbackRunOnUiServerAction<ApplicationMessage> {
        public static final String APPLICATION_DETAILS_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/";
        private int applicationId;
        private String applicationPackage;

        public DetailsAction(Activity activity) {
            super(activity);
        }

        public int getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(int applicationId) {
            this.applicationId = applicationId;
        }

        public String getApplicationPackage() {
            return applicationPackage;
        }

        public void setApplicationPackage(String applicationPackage) {
            this.applicationPackage = applicationPackage;
        }

        @Override
        protected ApplicationMessage handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url;
            if (applicationId > 0) {
                url = new URL(APPLICATION_DETAILS_QUERY + applicationId);
            } else {
                url = new URL(APPLICATION_DETAILS_QUERY + applicationPackage);
            }
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), ApplicationMessage.class);
        }

    }

    public static class SearchAction extends CallbackRunOnUiServerAction<ApplicationQueryMessage> {
        public static final String APPLICATION_SEARCH_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/Search/Pager/%d&kw=%s";
        private int pagerNumber = 1;
        private String keyword;

        public SearchAction(Activity activity) {
            super(activity);
        }

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            if (pagerNumber < 1) {
                pagerNumber = 1;
            }
            this.pagerNumber = pagerNumber;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        @Override
        protected ApplicationQueryMessage handle() throws IOException {
            String realURL = String.format(Locale.CANADA, APPLICATION_SEARCH_QUERY, pagerNumber, URLEncoder.encode(keyword));
            HttpClient httpClient = getHttpClient();
            URL url = new URL(realURL);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return JSONUtils.fromJSON(response.getResponseBody(), ApplicationQueryMessage.class);
        }
    }

    public static class ListAction extends CallbackRunOnUiServerAction<ApplicationQueryMessage> {

        public static final String APPLICATION_LIST_QUERY = ServerUtils.BASE_QUERY_URL + "/Apps/Pager/%d";

        private int pagerNumber;
        private ServerActionCallback<ApplicationQueryMessage, Message> callback;

        public ListAction(Activity activity) {
            super(activity);
        }

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            this.pagerNumber = pagerNumber;
        }

        @Override
        protected ApplicationQueryMessage handle() throws IOException {
            return null;
        }
    }
}
