package cn.com.sise.ca.castore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.facades.HomePageAdvertisementFacade;
import cn.com.sise.ca.castore.server.facades.HomePageBaseFacade;
import cn.com.sise.ca.castore.server.som.HomePageInfoBean;

public class HomePageAdapter extends BaseAdapter {
    List<HomePageInfoBean> list = new ArrayList<>();
    List<View> viewList = new ArrayList<>();

    public void add(HomePageInfoBean bean) {
        if (bean == null) {
            throw new IllegalArgumentException();
        }
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HomePageInfoBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewList.size() <= position) {
            HomePageInfoBean bean = getItem(position);
            View view;
            if (bean.getType() == HomePageInfoBean.POWERPOINT) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_powerpoint, parent, false);
                HomePageAdvertisementFacade homePageAdvertisementFacade = new HomePageAdvertisementFacade();
                homePageAdvertisementFacade.setView(view);
                homePageAdvertisementFacade.setHomePageInfoBean(bean);
                view.setTag(homePageAdvertisementFacade);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_base, parent, false);
                HomePageBaseFacade homePageBaseFacade = new HomePageBaseFacade();
                homePageBaseFacade.setView(view);
                homePageBaseFacade.setHomePageInfoBean(bean);
                view.setTag(homePageBaseFacade);
            }
            viewList.add(view);
        }
        return viewList.get(position);
    }
}
