package cn.com.sise.ca.castore.utils;

import android.support.v7.app.ActionBar;

import cn.com.sise.ca.castore.R;

public class ActionBarUtils {
    public static void showBackAsUp(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    };
}
