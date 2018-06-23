package cn.com.sise.ca.castore.server.facades;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.ApplicationInfoBean;

public class ApplicationSummaryFacade implements Facade {

    private Bitmap icon;
    private String title;
    private String description;
    private String resourceDescription;

    private ApplicationInfoBean applicationInfoBean;

    private ImageView viewIcon;
    private TextView viewTitle;
    private TextView viewDescription;
    private TextView viewResourceDescription;

    private View container;

    public ApplicationSummaryFacade(View container) {
        setView(container);
    }

    public ApplicationInfoBean getApplicationInfoBean() {
        return applicationInfoBean;
    }

    public void setApplicationInfoBean(ApplicationInfoBean applicationInfoBean) {
        this.applicationInfoBean = applicationInfoBean;
        setTitle(applicationInfoBean.getTitle());
        setDescription(applicationInfoBean.getDescription());
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
        viewIcon.setImageBitmap(icon);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        viewTitle.setText(title);
    }

    public String getShortDescription() {
        return description.substring(0, description.length() > 30 ? 30 : description.length());
    }
    public String getDescription() {
        return description;
    }

    public void setResourceDescription(String population, String size) {
        this.resourceDescription = String.format("%s | %s", population, size);
        viewResourceDescription.setText(resourceDescription);
    }

    public void setDescription(String description) {
        this.description = description;
        viewDescription.setText(getShortDescription());
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
        viewResourceDescription.setText(resourceDescription);
    }

    @Override
    public void setView(View view) {
        container = view;
        viewIcon = container.findViewById(R.id.som_app_icon);
        viewTitle = container.findViewById(R.id.som_app_title);
        viewDescription = container.findViewById(R.id.som_app_description);
        viewResourceDescription = container.findViewById(R.id.som_app_file_des);
    }

    @Override
    public View getView() {
        return container;
    }
}
