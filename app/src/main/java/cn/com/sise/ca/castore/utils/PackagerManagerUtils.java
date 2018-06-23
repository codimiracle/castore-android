package cn.com.sise.ca.castore.utils;

import android.content.Context;
import android.content.Intent;

public class PackagerManagerUtils {
    public static void startPackage(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(packageName);
        context.startActivity(intent);
    }

    public static void installPackage(Context context, String packageName) {

    }
}
