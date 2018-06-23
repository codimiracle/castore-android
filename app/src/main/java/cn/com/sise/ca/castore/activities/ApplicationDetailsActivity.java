package cn.com.sise.ca.castore.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.adapters.ApplicationDetailsPagerAdapter;
import cn.com.sise.ca.castore.contacts.ApplicationCommentFragment;
import cn.com.sise.ca.castore.contacts.ApplicationIntroductionFragment;
import cn.com.sise.ca.castore.server.ApplicationAction;
import cn.com.sise.ca.castore.server.ResourceAction;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.facades.ApplicationSummaryFacade;
import cn.com.sise.ca.castore.server.som.ApplicationMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.utils.ActionBarUtils;
import cn.com.sise.ca.castore.utils.ServerUtils;

/**
 * 应用详细信息
 */
public class ApplicationDetailsActivity extends ServerActionActivity implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "APP Details Activity";

    public static final String APPLICATION_DETAILS_ACTION = CAstoreApplication.PACKAGE_NAME + ".ApplicationDetails";
    public static final String PACKAGE_NAME_KEY = "package_name_key";
    public static final String APPLICATION_ID = "application_id";

    private int id;
    private String packageName;
    private TabLayout tabs;
    private ViewPager tabPagerContainer;

    private ApplicationDetailsPagerAdapter pagerAdapter;

    private ApplicationMessage applicationMessage = null;
    private ApplicationSummaryFacade applicationSummaryFacade;

    private ApplicationIntroductionFragment introductionFragment;
    private ApplicationCommentFragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarUtils.showBackAsUp(getSupportActionBar());

        Intent intent = getIntent();
        id = intent.getExtras().getInt(APPLICATION_ID, -1);
        packageName = intent.getExtras().getString(PACKAGE_NAME_KEY, null);

        introductionFragment = ApplicationIntroductionFragment.newInstance();
        commentFragment = ApplicationCommentFragment.newInstance();


        pagerAdapter = new ApplicationDetailsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(introductionFragment);
        pagerAdapter.addFragment(commentFragment);


        applicationSummaryFacade = new ApplicationSummaryFacade(findViewById(R.id.app_details_app_info));
        applicationSummaryFacade.getView().findViewById(R.id.som_app_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (applicationMessage.getAppPackage() == null) {
                    Toast.makeText(ApplicationDetailsActivity.this, "由于数据不完整，此应用无法下载！", Toast.LENGTH_SHORT).show();
                    return;
                }
                final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                String url = ServerUtils.BASE_URL + "/" + applicationMessage.getAppPackage().getPath();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                String destination = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/temp." + Math.random() + ".apk";
                ;
                Log.i(TAG, "downloadlink:" + url);
                Log.i(TAG, "destlink:" + destination);
                request.setDestinationUri(Uri.fromFile(new File(destination)));
                request.setTitle(applicationMessage.getAppInfo().getTitle());
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                final long downloadId = downloadManager.enqueue(request);
                IntentFilter filter = new IntentFilter();
                filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        String action = intent.getAction();
                        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                            if (id == downloadId) {
                                Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                                Intent i = new Intent();
                                i.setDataAndType(uri, downloadManager.getMimeTypeForDownloadedFile(downloadId));
                                startActivity(i);
                            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action)) {
                                downloadManager.remove(downloadId);
                            }
                        }
                    }
                };
                registerReceiver(receiver, filter);
            }
        });
        tabs = findViewById(R.id.app_details_tab_layout);
        tabs.addOnTabSelectedListener(this);
        tabPagerContainer = findViewById(R.id.app_details_tab_page_container);
        tabPagerContainer.setAdapter(pagerAdapter);
        tabPagerContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabs.setScrollPosition(position, 0, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        refresh();
    }

    private void refresh() {
        Log.i(TAG, "refresh start.");
        if (applicationMessage == null || applicationMessage.getAppInfo().getId() != id) {
            Log.i(TAG, "request start.");
            ApplicationAction.DetailsAction action = new ApplicationAction.DetailsAction(this);
            if (packageName != null) {
                action.setApplicationPackage(packageName);
            } else {
                action.setApplicationId(id);
            }

            action.setCallback(new SimpleServerActionCallback<ApplicationMessage>() {
                @Override
                public void onSuccess(ApplicationMessage message) {
                    Log.i(TAG, "request end.");
                    applicationMessage = message;
                    if (applicationMessage.getAppIcon() != null) {
                        ResourceAction.ImageCacheAction iconImageCacheAction = new ResourceAction.ImageCacheAction(ApplicationDetailsActivity.this);
                        iconImageCacheAction.setResourceURL(ServerUtils.BASE_URL + "/" + applicationMessage.getAppIcon().getPath());
                        iconImageCacheAction.setCallback(new SimpleServerActionCallback<Bitmap>() {
                            @Override
                            public void onSuccess(final Bitmap message) {
                                applicationSummaryFacade.setIcon(message);
                            }

                            @Override
                            public void onFailure(Message message) {
                                Log.i(TAG, "icon downloading failed!");
                            }
                        });
                        addServerAction(iconImageCacheAction);
                    }
                    applicationSummaryFacade.setApplicationInfoBean(applicationMessage.getAppInfo());
                    introductionFragment.setApplicationMessage(applicationMessage);
                    commentFragment.setApplicationMessage(applicationMessage);
                    getSupportActionBar().setTitle(applicationMessage.getAppInfo().getTitle());
                }

                @Override
                public void onFailure(Message message) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationDetailsActivity.this);
                    builder.setTitle("应用查询");
                    builder.setMessage("网络链接失败，请确认网络链接状态！");
                    builder.setPositiveButton(android.R.string.yes, null);
                    builder.show();
                }
            });
            addServerAction(action);
        }
        Log.i(TAG, "refresh end.");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabPagerContainer.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
