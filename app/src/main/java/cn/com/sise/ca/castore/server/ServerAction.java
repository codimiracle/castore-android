package cn.com.sise.ca.castore.server;

/**
 * {@code ServerAction}
 * 服务器动作用于把客户端与服务器进行数据交互（Interaction）
 * 既然进行数据交互，那必定会有发送和接收的过程
 *
 * @author Codimiracle
 */
public interface ServerAction {

    /**
     * execute server action and (maybe) fire the callback.
     */
    public void execute();
}
