package cn.com.sise.ca.castore.server;

public interface ServerActionCallback<T, P> {
    public void onSuccess(T message);
    public void onFailure(P message);
}
