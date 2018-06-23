package cn.com.sise.ca.castore.server;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.com.sise.ca.castore.components.BitmapCache;

public class ResourceAction {
    public static class ImageCacheAction extends CallbackRunOnUiServerAction<Bitmap> {
        private String resourceURL;

        public ImageCacheAction(Activity activity) {
            super(activity);
        }

        public String getResourceURL() {
            return resourceURL;
        }

        public void setResourceURL(String resourceURL) {
            this.resourceURL = resourceURL;
        }

        @Override
        protected Bitmap handle() throws IOException {
            if (BitmapCache.hasCache(resourceURL)) {
                return BitmapCache.get(resourceURL);
            }
            HttpClient httpClient = getHttpClient();
            URL url = new URL(resourceURL);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            InputStream inputStream = response.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        }
    }
}
