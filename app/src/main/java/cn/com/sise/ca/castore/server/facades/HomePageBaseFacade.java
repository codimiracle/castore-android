package cn.com.sise.ca.castore.server.facades;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.HomePageInfoBean;

public class HomePageBaseFacade implements Facade {

    private HomePageInfoBean homePageInfoBean;

    private View view;
    private PosterAlbumFacade posterAlbumFacade;
    private ArticleAlbumFacade articleAlbumFacade;
    private ApplicationSimpleFacade[] applicationSimpleFacade = new ApplicationSimpleFacade[4];

    private LinearLayout viewOddmentsApps;
    private TextView viewOddmentsTitle;

    public HomePageInfoBean getHomePageInfoBean() {
        return homePageInfoBean;
    }

    public void setHomePageInfoBean(HomePageInfoBean homePageInfoBean) {
        if (homePageInfoBean.getType() != HomePageInfoBean.PAGE) {
            throw new IllegalArgumentException();
        }
        this.homePageInfoBean = homePageInfoBean;
        viewOddmentsTitle.setText(homePageInfoBean.getOddmentsTitle());
        int childCount = viewOddmentsApps.getChildCount();
        for (int i = 0; i < childCount; i++) {
            applicationSimpleFacade[i] = new ApplicationSimpleFacade();
            applicationSimpleFacade[i].setView(viewOddmentsApps.getChildAt(i));
        }

    }

    @Override
    public void setView(View view) {
        this.view = view;
        viewOddmentsApps = view.findViewById(R.id.home_page_oddments_apps_container);
        viewOddmentsTitle = view.findViewById(R.id.home_page_oddments_apps_title);
        posterAlbumFacade = new PosterAlbumFacade(view.findViewById(R.id.home_page_poster_album));
        articleAlbumFacade = new ArticleAlbumFacade(view.findViewById(R.id.home_page_article_album));
    }

    @Override
    public View getView() {
        return view;
    }
}
