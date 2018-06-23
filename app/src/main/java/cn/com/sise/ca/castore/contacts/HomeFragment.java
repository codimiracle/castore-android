package cn.com.sise.ca.castore.contacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.adapters.HomePageAdapter;
import cn.com.sise.ca.castore.contacts.abstracts.HomeContact;
import cn.com.sise.ca.castore.server.HomeAction;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.som.HomePageInfoBean;
import cn.com.sise.ca.castore.server.som.HomePageMessage;
import cn.com.sise.ca.castore.server.som.Message;

public class HomeFragment extends ServerActionFragment implements HomeContact.View {
    private static final String TAG = "HomeFragment";

    private View loadingView;
    private HomeAction.HomePageAction homePageAction;
    private HomeAction.AdvertisementAction advertisementAction;
    private boolean loading = false;
    private SimpleServerActionCallback<HomePageMessage> callback = new SimpleServerActionCallback<HomePageMessage>() {
        @Override
        public void onSuccess(HomePageMessage message) {
            homePageMessage = message;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onNextPage(homePageMessage.getHomePageInfoBean());
                    if (homePageAction.getPagerNumber() < 1) {
                        loadNextPage();
                    }
                }
            });
        }

        @Override
        public void onFailure(Message message) {
            final String m = message.getMessage();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), m, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    private HomePageMessage homePageMessage;
    private static HomeFragment homeFragment;
    private ListView pageContainer;
    private HomePageAdapter homePageAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePageAdapter = new HomePageAdapter();
        homePageMessage = new HomePageMessage();
        advertisementAction = new HomeAction.AdvertisementAction(getActivity());
        advertisementAction.setCallback(callback);
        homePageAction = new HomeAction.HomePageAction(getActivity());
        homePageAction.setCallback(callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_home_fragment_view, container, false);
        pageContainer = view.findViewById(R.id.home_page_container);
        loadingView = inflater.inflate(R.layout.bottom_loading, pageContainer, false);
        pageContainer.addFooterView(loadingView);
        pageContainer.setAdapter(homePageAdapter);
        pageContainer.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.i(TAG, "TOP:" + loadingView.getTop());
                if (loadingView.getTop() < view.getHeight() - loadingView.getHeight() && !loading && homePageMessage != null) {
                    loadNextPage();
                    loading = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 15) {
                    homePageMessage = null;
                }
            }
        });
        return view;
    }

    private void loadNextPage() {
        homePageAction.setPagerNumber(homePageAction.getPagerNumber() + 1);
        addServerAction(homePageAction);
    }

    public void onNextPage(HomePageInfoBean bean) {
        if (bean == null) {
            Toast.makeText(getContext(), "网络链接失败或获取的数据格式不正确，请联系系统管理员解决问题！", Toast.LENGTH_SHORT).show();
            return;
        }
        homePageAdapter.add(bean);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addServerAction(advertisementAction);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPowerpoints() {

    }
}
