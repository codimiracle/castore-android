package cn.com.sise.ca.castore.server.facades;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.PosterAlbumInfoBean;

public class PosterAlbumFacade implements Facade {
    private View view;
    private ImageView viewPoster;
    private ImageView viewIcon;
    private TextView viewTitle;
    private PosterAlbumInfoBean posterAlbumInfoBean;
    private ApplicationSimpleFacade applicationSimpleFacade;


    public PosterAlbumInfoBean getPosterAlbumInfoBean() {
        return posterAlbumInfoBean;
    }

    public void setPosterAlbumInfoBean(PosterAlbumInfoBean posterAlbumInfoBean) {
        this.posterAlbumInfoBean = posterAlbumInfoBean;
        viewTitle.setText(posterAlbumInfoBean.getTitle());
        applicationSimpleFacade.setApplicationInfoBean(posterAlbumInfoBean.getApplicationInfoBean());
    }

    public PosterAlbumFacade(View view) {
        setView(view);
    }

    public void setPoster(Bitmap poster) {
        viewPoster.setImageBitmap(poster);
    }

    public void setIcon(Bitmap icon) {
        viewIcon.setImageBitmap(icon);
        applicationSimpleFacade.setIcon(icon);
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.viewPoster = view.findViewById(R.id.som_poster_album_poster);
        this.viewIcon = view.findViewById(R.id.som_poster_album_icon);
        this.viewTitle = view.findViewById(R.id.som_poster_album_title);
        this.applicationSimpleFacade = new ApplicationSimpleFacade();
        this.applicationSimpleFacade.setView(view.findViewById(R.id.som_main_op_component));
    }

    @Override
    public View getView() {
        return view;
    }
}
