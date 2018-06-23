package cn.com.sise.ca.castore.server.facades;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.ApplicationInfoBean;

public class ApplicationSimpleFacade implements Facade {
    private Bitmap icon;
    private String name;
    private String people;

    private ImageView viewIcon;
    private TextView viewName;
    private TextView viewPeople;


    private View view;
    private ApplicationInfoBean applicationInfoBean;

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
        viewIcon.setImageBitmap(icon);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        viewName.setText(name);
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
        viewPeople.setText(people);
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.viewIcon = view.findViewById(R.id.som_app_icon);
        this.viewName = view.findViewById(R.id.som_app_name);
        this.viewPeople = view.findViewById(R.id.som_app_people);
    }

    @Override
    public View getView() {
        return view;
    }

    public void setApplicationInfoBean(ApplicationInfoBean applicationInfoBean) {
        this.applicationInfoBean = applicationInfoBean;
        this.viewName.setText(applicationInfoBean.getName());
    }

    public ApplicationInfoBean getApplicationInfoBean() {
        return applicationInfoBean;
    }
}
