package cn.com.sise.ca.castore.server;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.URL;

import cn.com.sise.ca.castore.server.som.ArticleAlbumMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class ArticleAlbumAction {
    public static class DetailsAction implements ServerAction {
        public static final String ARTICALE_ALBUM_DETAILS = ServerUtils.BASE_QUERY_URL + "/ArticleAlbum/";

        private int articleAlbumId;
        private ServerActionCallback<ArticleAlbumMessage, Message> callback;

        public int getArticleAlbumId() {
            return articleAlbumId;
        }

        public void setArticleAlbumId(int articleAlbumId) {
            this.articleAlbumId = articleAlbumId;
        }

        public void setCallback(ServerActionCallback<ArticleAlbumMessage, Message> callback) {
            this.callback = callback;
        }

        public ServerActionCallback<ArticleAlbumMessage, Message> getCallback() {
            return callback;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(ARTICALE_ALBUM_DETAILS + articleAlbumId);
                httpClient.open(url);
                HttpResponse response = httpClient.getHttpResponse();
                ArticleAlbumMessage message = JSONUtils.fromJSON(response.getResponseBody(), ArticleAlbumMessage.class);
                if (message != null) {
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
