package cn.com.sise.ca.castore.server;

import android.app.Activity;

import com.codimiracle.libs.lumehttp.HttpClient;

import java.io.IOException;

import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.HttpUtils;

public abstract class CallbackRunOnUiServerAction<T> implements ServerAction {
    private HttpClient httpClient;
    private Activity activity;

    public CallbackRunOnUiServerAction(Activity activity) {
        this.activity = activity;
        httpClient = HttpUtils.getHttpClient();
    }
    protected abstract T handle() throws IOException;
    ServerActionCallback<T, Message> callback;

    public void setCallback(ServerActionCallback<T, Message> callback) {
        this.callback = callback;
    }

    public ServerActionCallback<T, Message> getCallback() {
        return callback;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public void execute() {
        try {
            final T m = handle();
            if (m!= null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(m);
                    }
                });
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
                }
            });
        } finally {
            httpClient.close();
        }
    }
}
