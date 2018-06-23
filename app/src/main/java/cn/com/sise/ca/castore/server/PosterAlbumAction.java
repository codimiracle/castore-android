package cn.com.sise.ca.castore.server;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.URL;

import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.server.som.PosterAlbumMessage;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class PosterAlbumAction {
    public static class DetailsAction implements  ServerAction{
        public static final String POSTER_ALBUM_DETAILS = ServerUtils.BASE_QUERY_URL + "/PosterAlbum/";

        private ServerActionCallback<PosterAlbumMessage, Message> callback;
        private int posterAlbumId;

        public int getPosterAlbumId() {
            return posterAlbumId;
        }

        public void setCallback(ServerActionCallback<PosterAlbumMessage, Message> callback) {
            this.callback = callback;
        }

        public ServerActionCallback<PosterAlbumMessage, Message> getCallback() {
            return callback;
        }

        public void setPosterAlbumId(int posterAlbumId) {
            this.posterAlbumId = posterAlbumId;
        }


        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(POSTER_ALBUM_DETAILS + posterAlbumId);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                PosterAlbumMessage message = JSONUtils.fromJSON(response.getResponseBody(), PosterAlbumMessage.class);
                if (message!=null) {
                    callback.onSuccess(message);
                } else {
                    callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
            } finally {
                httpClient.close();
            }
        }
    }
}
