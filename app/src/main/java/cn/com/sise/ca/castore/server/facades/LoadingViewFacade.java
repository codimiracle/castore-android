package cn.com.sise.ca.castore.server.facades;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;

public class LoadingViewFacade implements Facade {
    private View view;
    private TextView viewPromote;
    private ImageView viewIcon;
    private ProgressBar viewProgress;
    @Override
    public void setView(View view) {
        this.view = view;
        viewPromote = view.findViewById(R.id.loading_view_promote);
        viewIcon = view.findViewById(R.id.loading_view_icon);
        viewProgress = view.findViewById(R.id.loading_view_progress);
    }

    @Override
    public View getView() {
        return view;
    }
}
