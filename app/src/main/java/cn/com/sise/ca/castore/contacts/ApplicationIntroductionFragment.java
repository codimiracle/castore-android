package cn.com.sise.ca.castore.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.activities.ApplicationDetailsActivity;
import cn.com.sise.ca.castore.adapters.PowerpointAdapter;
import cn.com.sise.ca.castore.server.ApplicationAction;
import cn.com.sise.ca.castore.server.ResourceAction;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.facades.ApplicationSummaryFacade;
import cn.com.sise.ca.castore.server.som.ApplicationMessage;
import cn.com.sise.ca.castore.server.som.Message;
import cn.com.sise.ca.castore.server.som.ResourceInfoBean;
import cn.com.sise.ca.castore.utils.ServerUtils;

public class ApplicationIntroductionFragment extends ServerActionFragment {
    public static final String TAG = "APP INTRO FRGM";

    private TextView viewDescription;
    private RecyclerView viewPowerpoints;
    private RecyclerView viewTags;

    private PowerpointAdapter powerpointAdapter;

    private ApplicationMessage applicationMessage = null;


    public static ApplicationIntroductionFragment newInstance() {
        Bundle args = new Bundle();
        ApplicationIntroductionFragment fragment = new ApplicationIntroductionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ApplicationMessage getApplicationMessage() {
        return applicationMessage;
    }

    public void setApplicationMessage(ApplicationMessage applicationMessage) {
        this.applicationMessage = applicationMessage;
        refresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appliciation_introduction, container, false);
        viewDescription = view.findViewById(R.id.app_intro_descritpion);
        viewPowerpoints = view.findViewById(R.id.app_intro_powerpoints);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        viewPowerpoints.setLayoutManager(linearLayoutManager);
        powerpointAdapter = new PowerpointAdapter();
        viewPowerpoints.setAdapter(powerpointAdapter);
        viewTags = view.findViewById(R.id.app_intro_tags);
        return view;
    }

    private void refresh() {
        if (applicationMessage == null)
            return;
        viewDescription.setText(applicationMessage.getAppInfo().getDescription());
        ResourceAction.ImageCacheAction powerpointAction;
        SimpleServerActionCallback<Bitmap> powerpointCallback = new SimpleServerActionCallback<Bitmap>() {
            @Override
            public void onSuccess(final Bitmap message) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        powerpointAdapter.addPowerpoint(message);
                    }
                });
            }

            @Override
            public void onFailure(Message message) {
                Log.i(TAG, "Powerpoint downloading faild!");
            }
        };
        for (ResourceInfoBean resourceInfoBean : applicationMessage.getAppPowerpoints()) {
            powerpointAction = new ResourceAction.ImageCacheAction(getActivity());
            powerpointAction.setResourceURL(ServerUtils.BASE_URL + "/" + resourceInfoBean.getPath());
            powerpointAction.setCallback(powerpointCallback);
            addServerAction(powerpointAction);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
