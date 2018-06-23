package cn.com.sise.ca.castore.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.AdvertisementInfoBean;

public class AdvertisementAdapter extends BaseAdapter {
    List<AdvertisementInfoBean> advertisements;

    public void setAdvertisements(List<AdvertisementInfoBean> advertisements) {
        this.advertisements = advertisements;
        notifyDataSetChanged();
    }

    public List<AdvertisementInfoBean> getAdvertisements() {
        return advertisements;
    }

    @Override
    public int getCount() {
        return 5/*advertisements.size()*/;
    }

    @Override
    public AdvertisementInfoBean getItem(int position) {
        return advertisements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisement_item, parent, false);
        ImageView imageView = view.findViewById(R.id.advertisement_poster);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        return view;
    }
}
