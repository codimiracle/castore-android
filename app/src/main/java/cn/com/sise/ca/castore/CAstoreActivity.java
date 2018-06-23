package cn.com.sise.ca.castore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cn.com.sise.ca.castore.activities.ApplicationQueryActivity;
import cn.com.sise.ca.castore.activities.DownloadManagerActivity;
import cn.com.sise.ca.castore.activities.UserProfileActivity;
import cn.com.sise.ca.castore.activities.UserSessionActivity;
import cn.com.sise.ca.castore.contacts.CategoryFragment;
import cn.com.sise.ca.castore.contacts.HomeFragment;
import cn.com.sise.ca.castore.contacts.ManagerFragment;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.UserAction;
import cn.com.sise.ca.castore.server.facades.UserAsideBarFacade;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.server.som.UserMessage;
import cn.com.sise.ca.castore.utils.FragmentUtils;
import cn.com.sise.ca.castore.utils.ServerActionServiceTool;
import cn.com.sise.ca.castore.utils.ServerActionServiceUtils;

public class CAstoreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;
    private CAstoreApplication castoreApplication;
    private ServerActionServiceTool serverActionServiceTool;
    private ImageView userAvatar;
    private UserAsideBarFacade userAsideBarFacador;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnBottomNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return onBottomNavigationItemSelected(item);
        }
    };

    private NavigationView userCenterPanel;
    private CategoryFragment categoryFragment;
    private ManagerFragment managerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //底部栏
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnBottomNavigationItemSelectedListener);

        //用户栏
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        userCenterPanel = (NavigationView) findViewById(R.id.nav_view);
        userCenterPanel.setNavigationItemSelectedListener(this);

        userAsideBarFacador = new UserAsideBarFacade();
        userAsideBarFacador.setView(userCenterPanel.getHeaderView(0));

        userAvatar = userCenterPanel.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!castoreApplication.isLogged()) {
                    Intent intent = new Intent(UserSessionActivity.SIGNIN_ACTION);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CAstoreActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        serverActionServiceTool = ServerActionServiceUtils.getTool(this);
        serverActionServiceTool.start();

        castoreApplication = (CAstoreApplication) getApplication();
        //Tab 页
        homeFragment = HomeFragment.newInstance();
        homeFragment.setServerActionServiceTool(serverActionServiceTool);
        categoryFragment = CategoryFragment.newInstance();
        managerFragment = ManagerFragment.newInstance();

        navigation.setSelectedItemId(R.id.navigation_home);
    }



    @Override
    protected void onResume() {
        super.onResume();
        serverActionServiceTool.bind();
        if (castoreApplication.isLogged() && castoreApplication.getUserInfoBean() == null) {
            UserAction.UserProfileAction userProfileAction = new UserAction.UserProfileAction();
            userProfileAction.setCallback(new SimpleServerActionCallback<UserMessage>() {
                @Override
                public void onSuccess(UserMessage message) {
                    castoreApplication.setUserInfoBean(message.getUserInfoBean());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userAsideBarFacador.setUserInfoBean(castoreApplication.getUserInfoBean());
                        }
                    });
                }

                @Override
                public void onFailure(Message message) {
                    Toast.makeText(CAstoreActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            serverActionServiceTool.addServerAction(userProfileAction);
        } else if (castoreApplication.isLogged()) {
            userAsideBarFacador.setUserInfoBean(castoreApplication.getUserInfoBean());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //操作栏处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_downloads) {
            startActivity(DownloadManagerActivity.DOWNLOAD_MANAGER_ACTION);
            return true;
        } else if (id == R.id.action_search) {
            startActivity(ApplicationQueryActivity.APPLICATION_QUERY_ACTION);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startActivity(String action) {
        Intent intent = new Intent(action);
        startActivity(intent);
    }
    //用户栏处理
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.user_aside_bar_favourite) {
            startActivity(UserProfileActivity.USER_FAVOURITE_ACTION);
        } else if (id == R.id.user_aside_bar_personal) {
            startActivity(UserProfileActivity.USER_PERSONAL_ACTION);
        } else if (id == R.id.user_aside_bar_preferences) {
            startActivity(UserProfileActivity.USER_PREFERENCE_ACTION);
        } else if (id == R.id.user_aside_bar_share) {

        } else if (id == R.id.user_aside_bar_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //底部栏处理
    public boolean onBottomNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.main_container, homeFragment);
                return true;
            case R.id.navigation_category:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.main_container, categoryFragment);
                return true;
            case R.id.navigation_discover:
                return true;
            case R.id.navigation_manager:
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.main_container, managerFragment);
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        serverActionServiceTool.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverActionServiceTool.stop();
    }
}
