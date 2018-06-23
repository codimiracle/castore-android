package cn.com.sise.ca.castore.contacts;

import android.content.Context;
import android.support.v4.app.Fragment;

import cn.com.sise.ca.castore.activities.ServerActionActivity;
import cn.com.sise.ca.castore.server.ServerAction;
import cn.com.sise.ca.castore.utils.ServerActionServiceTool;
import cn.com.sise.ca.castore.utils.ServerActionServiceUtils;

public class ServerActionFragment extends Fragment {
    private boolean stanlone = false;
    private ServerActionServiceTool serverActionServiceTool;

    public ServerActionServiceTool getServerActionServiceTool() {
        return serverActionServiceTool;
    }

    public void setServerActionServiceTool(ServerActionServiceTool serverActionServiceTool) {
        this.serverActionServiceTool = serverActionServiceTool;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (serverActionServiceTool == null) {
            if (context instanceof ServerActionActivity) {
                serverActionServiceTool = ((ServerActionActivity) context).getServerActionServiceTool();
            } else {
                serverActionServiceTool = ServerActionServiceUtils.getTool(context);
                serverActionServiceTool.bind();
                stanlone = true;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (stanlone) {
            serverActionServiceTool.unbind();
        }
    }

    public void addServerAction(ServerAction action) {
        serverActionServiceTool.addServerAction(action);
    }


}
