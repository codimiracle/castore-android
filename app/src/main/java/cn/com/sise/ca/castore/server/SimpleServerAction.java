package cn.com.sise.ca.castore.server;

import java.io.IOException;

import cn.com.sise.ca.castore.server.som.Message;

public abstract class SimpleServerAction<T> implements ServerAction {
    protected abstract T handler() throws IOException;
    ServerActionCallback<T, Message> callback;

    public void setCallback(ServerActionCallback<T, Message> callback) {
        this.callback = callback;
    }

    public ServerActionCallback<T, Message> getCallback() {
        return callback;
    }

    @Override
    public void execute() {
        try {
            T m = handler();
            if (m!= null) {
                callback.onSuccess(m);
            } else {
                callback.onFailure(Message.DATAFORMAT_ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(Message.INTERNET_ERROR_MESSAGE);
        }
    }
}
