package cn.com.sise.ca.castore.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.com.sise.ca.castore.server.ServerAction;
import cn.com.sise.ca.castore.utils.ServerActionServiceTool;
import cn.com.sise.ca.castore.utils.ServerActionServiceUtils;

/**
 * 进一步简化 服务器动作服务 的使用。
 */
public class ServerActionActivity extends BaseActivity {

    private ServerActionServiceTool serverActionServiceTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serverActionServiceTool = ServerActionServiceUtils.getTool(this);
        serverActionServiceTool.bind();
    }

    public void addServerAction(ServerAction action) {
        serverActionServiceTool.addServerAction(action);
    }

    public ServerActionServiceTool getServerActionServiceTool() {
        return serverActionServiceTool;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverActionServiceTool.unbind();
    }
}
