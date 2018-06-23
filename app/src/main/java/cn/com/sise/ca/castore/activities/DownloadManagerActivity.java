package cn.com.sise.ca.castore.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;

public class DownloadManagerActivity extends BaseActivity {
    public static final String DOWNLOAD_MANAGER_ACTION = CAstoreApplication.PACKAGE_NAME + ".DownloadManager";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
    }

    

}
