package cn.com.sise.ca.castore.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentUtils {

    public static void replaceFragment(FragmentManager fragmentManager, int container, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(container, fragment)
                .commit();
    }

}
