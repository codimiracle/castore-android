package cn.com.sise.ca.castore.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.codimiracle.libs.lumehttp.HttpClient;
import com.codimiracle.libs.lumehttp.HttpResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import cn.com.sise.ca.castore.utils.HttpUtils;

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private static final String DOWNLOAD_SETTING_SP = "settings.download";
    private static final String DOWNLOAD_THREAD_COUNT = "download_thread_count";

    private Queue<String> queue;
    private Thread[] threads;
    private final Object[] queueLock = new Object[0];
    private final Object[] initializeLock = new Object[0];
    private boolean threadCreated = false;
    private int threadCount;
    private boolean interruption;
    private SharedPreferences downloadSettings;

    private Runnable downloader = new Runnable() {
        @Override
        public void run() {
            while (!interruption) {
                synchronized (queueLock) {
                    String urlStr = queue.poll();
                    if (urlStr != null) {
                        try {
                            URL url = new URL(urlStr);
                            HttpClient httpClient = HttpUtils.getHttpClient();
                            httpClient.open(url);
                            HttpResponse response = httpClient.getHttpResponse();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadSettings = getBaseContext().getSharedPreferences(DOWNLOAD_SETTING_SP, MODE_PRIVATE);
        threadCount = downloadSettings.getInt(DOWNLOAD_THREAD_COUNT, 3);
        queue = new LinkedList<>();
        threads = new Thread[threadCount];
        Log.i(TAG, TAG + "is started " + threadCount + " threading.");
    }
    private void run() {
        synchronized (initializeLock) {
            if (!threadCreated) {
                for (int i = 0; i < threadCount; i++) {
                    threads[i] = new Thread(downloader);
                    threads[i].setName(TAG + " Thread[" + i + "]");
                    threads[i].start();
                }
                Log.i(TAG, "thread is created.");
            } else {
                Log.i(TAG, "thread is already create.");
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interruption = true;
    }
}
