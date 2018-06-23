package cn.com.sise.ca.castore.contacts.abstracts;

import android.widget.Adapter;

import cn.com.sise.ca.castore.framework.BasePresenter;
import cn.com.sise.ca.castore.framework.BaseView;

public interface HomeContact {
    public interface View extends BaseView{
        public void setPowerpoints();
    }

    public interface Presenter extends BasePresenter<View> {
        public void onNextPageLoad();
    }
}
