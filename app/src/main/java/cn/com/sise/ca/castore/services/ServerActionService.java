package cn.com.sise.ca.castore.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

import cn.com.sise.ca.castore.server.ServerAction;

public class ServerActionService extends Service {
    public class ActionServiceBinder extends Binder {
        public void add(ServerAction action) {
            queue.add(action);
        }
    }

    protected static final String TAG = "ServerActionService";

    protected static final String SETTING_SP_NAME = "setting.threads";
    protected static final String ACTION_THREAD_COUNT = "action_thread_count";

    private SharedPreferences actionThreadSettings;
    private Queue<ServerAction> queue;
    private final Object[] queueLock = new Object[0];
    private int threadCount;
    private Thread[] threads;
    private boolean threadCreated = false;
    private final Object[] initializeLock = new Object[0];
    private boolean interruption = false;
    private Runnable serverActionExecutor = new Runnable() {
        @Override
        public void run() {
            while (!interruption) {
                ServerAction action;
                synchronized (queueLock) {
                    action = queue.poll();
                }
                if (action != null) {
                    action.execute();
                }
                try {
                    Thread.sleep(300); //节省系统资源
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, Thread.currentThread().getName() + " is terminated.");
        }
    };

    public ServerActionService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        actionThreadSettings = getBaseContext().getSharedPreferences(SETTING_SP_NAME, Context.MODE_PRIVATE);
        threadCount = actionThreadSettings.getInt(ACTION_THREAD_COUNT, 3);
        queue = new LinkedList<>();
        threads = new Thread[threadCount];
        Log.i(TAG, "Thread Count is initialized to " + threadCount);
    }

    private void run() {
        //确保不会被多次生成线程占用不必要的资源
        synchronized (initializeLock) {
            if (!threadCreated) {
                for (int i = 0; i < threads.length; i++) {
                    threads[i] = new Thread(serverActionExecutor);
                    threads[i].setName(TAG + " Theard[" + i + "]");
                    threads[i].start();
                }
                threadCreated = true;
                Log.i(TAG, "Thread is created.");
            } else {
                Log.i(TAG, "Thread is aleady created.");
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ActionServiceBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interruption = true;
        Log.i(TAG, "destroy.");
    }
}
