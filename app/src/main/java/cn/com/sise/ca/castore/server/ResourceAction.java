package cn.com.sise.ca.castore.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.InputStream;
import java.net.URL;

import cn.com.sise.ca.castore.components.BitmapCache;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;

public class ResourceAction {
    public static class ImageCacheAction implements ServerAction {
        private String resourceURL;
        private ServerActionCallback<Bitmap, Message> callback;

        public ServerActionCallback<Bitmap, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<Bitmap, Message> callback) {
            this.callback = callback;
        }

        public String getResourceURL() {
            return resourceURL;
        }

        public void setResourceURL(String resourceURL) {
            this.resourceURL = resourceURL;
        }

        @Override
        public void execute() {
            if (BitmapCache.hasCache(resourceURL)) {
                callback.onSuccess(BitmapCache.get(resourceURL));
                return;
            }
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(resourceURL);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                InputStream inputStream = response.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    BitmapCache.put(resourceURL, bitmap);
                    callback.onSuccess(bitmap);
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
