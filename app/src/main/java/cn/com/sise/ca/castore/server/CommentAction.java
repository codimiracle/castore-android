package cn.com.sise.ca.castore.server;

import android.app.Activity;

import com.codimiracle.libs.lumehttp.FormData;
import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpRequest;
import com.codimiracle.libs.lumehttp.HttpResponse;
import com.codimiracle.libs.lumehttp.URLEncodedFormData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.server.som.CommentMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;
import cn.com.sise.ca.castore.utils.JSONUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class CommentAction {
    public static class CommentItAction implements ServerAction {
        public static final String COMMENT_APPEND = ServerUtils.BASE_QUERY_URL + "/Comment/Append";

        private ServerActionCallback<Message, Message> callback;

        private List<String> topics;
        private String comment;
        private int targetId;

        public CommentItAction() {
            topics = new ArrayList<>();
        }

        public List<String> getTopics() {
            return topics;
        }
        public void addTopic(String topic) {
            this.topics.add(topic);
        }
        public void setTopics(List<String> topics) {
            this.topics = topics;
        }

        public int getTargetId() {
            return targetId;
        }

        public void setTargetId(int targetId) {
            this.targetId = targetId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public ServerActionCallback<Message, Message> getCallback() {
            return callback;
        }

        public void setCallback(ServerActionCallback<Message, Message> callback) {
            this.callback = callback;
        }

        @Override
        public void execute() {
            HttpClient httpClient = HttpUtils.getHttpClient();
            try {
                URL url = new URL(COMMENT_APPEND);
                httpClient.open(url);
                //生成topic数据。
                StringBuilder builder = new StringBuilder();
                String step = "";
                for (String topic: topics) {
                    //去除不合法的 ','字符。
                    builder.append(topic.replaceAll(",", ""));
                    builder.append(step);
                    step = ",";
                }
                FormData formData = new URLEncodedFormData();
                formData.put("title", builder.toString());
                formData.put("content", comment);
                formData.put("aid", "" + getTargetId());
                formData.put("comment_append", "comment_append");

                HttpRequest request = httpClient.getHttpRequest();
                request.setFormData(formData);
                HttpResponse response = httpClient.getHttpResponse();
                Message message = JSONUtils.fromJSON(response.getResponseBody(), Message.class);
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

    public static class CommentsListActionCallback extends CallbackRunOnUiServerAction<CommentMessage> {
        public static final String COMMENT_LIST_QUERY = ServerUtils.BASE_QUERY_URL + "/Comment/Pager/";
        private int targetId;
        private int pagerNumber;

        public int getTargetId() {
            return targetId;
        }

        public void setTargetId(int targetId) {
            this.targetId = targetId;
        }

        public int getPagerNumber() {
            return pagerNumber;
        }

        public void setPagerNumber(int pagerNumber) {
            this.pagerNumber = pagerNumber;
        }

        public CommentsListActionCallback(Activity activity) {
            super(activity);
        }

        @Override
        protected CommentMessage handle() throws IOException {
            String tempURL = COMMENT_LIST_QUERY + pagerNumber + "/" + targetId;
            URL url = new URL(tempURL);
            getHttpClient().open(url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpResponse response = getHttpClient().getHttpResponse();
            return JSONUtils.fromJSON(response.getResponseBody(), CommentMessage.class);
        }
    }
    public static class CommentEditAction implements ServerAction {

        @Override
        public void execute() {

        }
    }
}
