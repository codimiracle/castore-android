package cn.com.sise.ca.castore.server.facades;

import android.view.View;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.ArticleAlbumInfoBean;

public class ArticleAlbumFacade implements Facade {
    private View view;
    private TextView viewTitle;
    private TextView viewSummary;
    private TextView viewAuthor;

    private ApplicationSummaryFacade applicationSummaryFacade;
    private ArticleAlbumInfoBean articleAlbumInfoBean;

    public ArticleAlbumInfoBean getArticleAlbumInfoBean() {
        return articleAlbumInfoBean;
    }

    public void setArticleAlbumInfoBean(ArticleAlbumInfoBean articleAlbumInfoBean) {
        this.articleAlbumInfoBean = articleAlbumInfoBean;
        viewTitle.setText(articleAlbumInfoBean.getTitle());
        viewSummary.setText(articleAlbumInfoBean.getSummaryShort());
        viewAuthor.setText(articleAlbumInfoBean.getAuthor());
        applicationSummaryFacade.setApplicationInfoBean(articleAlbumInfoBean.getApplicationInfoBean());
    }

    public ArticleAlbumFacade(View view) {
        setView(view);
    }

    @Override
    public void setView(View view) {
        this.view = view;
        viewTitle = view.findViewById(R.id.som_article_album_title);
        viewAuthor = view.findViewById(R.id.som_article_album_author);
        viewSummary = view.findViewById(R.id.som_article_album_summary);
        applicationSummaryFacade = new ApplicationSummaryFacade(view.findViewById(R.id.som_app_info));
    }

    @Override
    public View getView() {
        return view;
    }
}
