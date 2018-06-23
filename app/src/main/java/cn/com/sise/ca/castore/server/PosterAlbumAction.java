package cn.com.sise.ca.castore.server;

import android.app.Activity;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.URL;

import cn.com.sise.ca.castore.server.som.PosterAlbumMessage;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class PosterAlbumAction {
    public static class DetailsAction extends CallbackRunOnUiServerAction<PosterAlbumMessage> {
        public static final String POSTER_ALBUM_DETAILS = ServerUtils.BASE_QUERY_URL + "/PosterAlbum/";

        private int posterAlbumId;

        public DetailsAction(Activity activity) {
            super(activity);
        }

        public int getPosterAlbumId() {
            return posterAlbumId;
        }

        public void setPosterAlbumId(int posterAlbumId) {
            this.posterAlbumId = posterAlbumId;
        }


        @Override
        public PosterAlbumMessage handle() throws IOException {
            HttpClient httpClient = HttpUtils.getHttpClient();
            URL url = new URL(POSTER_ALBUM_DETAILS + posterAlbumId);
            httpClient.open(url);
            HttpResponse response = httpClient.getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), PosterAlbumMessage.class);
        }
    }
}
