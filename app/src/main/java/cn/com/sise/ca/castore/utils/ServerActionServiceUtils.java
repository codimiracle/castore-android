package cn.com.sise.ca.castore.utils;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import cn.com.sise.ca.castore.services.ServerActionService;

public class ServerActionServiceUtils {
    public static ServerActionServiceTool getTool(Context context) {
        return new ServerActionServiceTool(context);
    }
}
