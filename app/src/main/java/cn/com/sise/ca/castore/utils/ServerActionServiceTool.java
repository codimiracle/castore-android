package cn.com.sise.ca.castore.utils;

import android.content.Context;
import android.content.Intent;

import cn.com.sise.ca.castore.server.ServerAction;
import cn.com.sise.ca.castore.services.ServerActionService;
import cn.com.sise.ca.castore.services.ServerActionServiceConnection;

/**
 * 使 服务器动作服务 的使用更简单
 */
public class ServerActionServiceTool {
    private Context context;
    private Intent intent;
    private ServerActionServiceConnection connection;

    public ServerActionServiceTool(Context context) {
        this.context = context;
        intent = new Intent(context, ServerActionService.class);
        connection = new ServerActionServiceConnection();
    }

    public void addServerAction(ServerAction action) {
        connection.addServerAction(action);
    }

    public void start() {
        context.startService(intent);
    }

    public void bind() {
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void unbind() {
        context.unbindService(connection);
    }

    public void stop() {
        context.stopService(intent);
    }

    public Intent getServiceIntent() {
        return intent;
    }
}