package cn.com.sise.ca.castore.server;

import android.app.Activity;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.URL;

import cn.com.sise.ca.castore.server.som.HomePageMessage;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class HomeAction {
    public static class AdvertisementAction extends CallbackRunOnUiServerAction<HomePageMessage> {
        public static final String HOME_PAGE_ADVERTISEMENT_QUERY = ServerUtils.BASE_QUERY_URL + "/Advertisement";

        public AdvertisementAction(Activity activity) {
            super(activity);
        }

        @Override
        public HomePageMessage handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url = new URL(HOME_PAGE_ADVERTISEMENT_QUERY);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), HomePageMessage.class);
        }
    }

    public static class HomePageAction extends CallbackRunOnUiServerAction<HomePageMessage> {
        public static final String HOME_PAGE_QUERY = ServerUtils.BASE_QUERY_URL + "/Home/Pager/";
        private int pagerNumber = 1;

        public HomePageAction(Activity activity) {
            super(activity);
        }

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            this.pagerNumber = pagerNumber;
        }

        @Override
        public HomePageMessage handle() throws IOException {
            HttpClient httpClient = getHttpClient();
            URL url = new URL(HOME_PAGE_QUERY + pagerNumber);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), HomePageMessage.class);
        }
    }
}
