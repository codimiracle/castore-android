package cn.com.sise.ca.castore.utils;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpRequest;

import java.net.CookieManager;

public class HttpUtils {
    private static CookieManager cookieManager = new CookieManager();

    public static HttpClient getHttpClient() {
        HttpClient client = new HttpClient();
        client.setUserAgent("CAstore/1.0");
        client.setCookieManager(cookieManager);
        return client;
    }

}
