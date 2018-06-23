package cn.com.sise.ca.castore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.facades.ApplicationSummaryFacade;
import cn.com.sise.ca.castore.server.som.ApplicationInfoBean;

public class SimpleApplicationAdapter extends BaseAdapter {
    private List<ApplicationInfoBean> applicationList = new ArrayList<>();

    @Override
    public int getCount() {
        return applicationList.size();
    }

    public void addPageList(List<ApplicationInfoBean> list) {
        applicationList.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public ApplicationInfoBean getItem(int position) {
        return applicationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_app_summary_item, parent, false);
            ApplicationSummaryFacade facador = new ApplicationSummaryFacade(convertView);
            facador.setApplicationInfoBean(getItem(position));
            convertView.setTag(facador);
        } else {
            ApplicationSummaryFacade facador = (ApplicationSummaryFacade) convertView.getTag();
            facador.setApplicationInfoBean(getItem(position));
        }
        return convertView;
    }
}
