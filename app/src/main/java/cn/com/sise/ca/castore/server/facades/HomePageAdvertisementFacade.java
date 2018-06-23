package cn.com.sise.ca.castore.server.facades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;

import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.AdvertisementInfoBean;
import cn.com.sise.ca.castore.server.som.HomePageInfoBean;

public class HomePageAdvertisementFacade implements Facade {

    private HomePageInfoBean homePageInfoBean;
    private View view;
    private AdapterViewFlipper viewCarousel;
    private List<Bitmap> powerpoints;

    public HomePageInfoBean getHomePageInfoBean() {
        return homePageInfoBean;
    }

    public void setHomePageInfoBean(HomePageInfoBean homePageInfoBean) {
        if (homePageInfoBean.getType() != HomePageInfoBean.POWERPOINT) {
            throw new IllegalArgumentException();
        }
        this.homePageInfoBean = homePageInfoBean;
    }

    public List<Bitmap> getPowerpoints() {
        return powerpoints;
    }

    public void setPowerpoints(List<Bitmap> powerpoints) {
        this.powerpoints = powerpoints;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        viewCarousel = view.findViewById(R.id.home_powerpoint_flipper);
        viewCarousel.setAutoStart(true);
        viewCarousel.setFlipInterval(3000);
        viewCarousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdvertisementInfoBean advertisement = homePageInfoBean.getPowerpoints().get(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(advertisement.getUrl()));
            }
        });
    }

    @Override
    public View getView() {
        return view;
    }


}
