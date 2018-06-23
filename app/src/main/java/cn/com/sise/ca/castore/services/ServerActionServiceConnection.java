package cn.com.sise.ca.castore.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.LinkedList;
import java.util.Queue;

import cn.com.sise.ca.castore.server.ServerAction;

public class ServerActionServiceConnection implements ServiceConnection{
    private ServerActionService.ActionServiceBinder binder;
    private Queue<ServerAction> waitingQueue = new LinkedList<>();

    public void addServerAction(ServerAction action) {
        //当添加过快的时候，放到等待队列。
        if (binder != null) {
            binder.add(action);
        } else {
            waitingQueue.add(action);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (ServerActionService.ActionServiceBinder) service;
        //把等待队列的服务器动作放到 ServerActionService 中。
        ServerAction action = null;
        while ((action = waitingQueue.poll()) != null) {
            binder.add(action);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
