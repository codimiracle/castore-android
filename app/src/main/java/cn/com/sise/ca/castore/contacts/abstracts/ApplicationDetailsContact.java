package cn.com.sise.ca.castore.contacts.abstracts;

import cn.com.sise.ca.castore.framework.BasePresenter;
import cn.com.sise.ca.castore.framework.BaseView;

public interface ApplicationDetailsContact {
    public interface View extends BaseView {
        public int getId();
        public void setTitle(String title);
        public void setSummary(String summary);
        public void setSize(String size);
        public void setPopularity(long popularity);
    }

    public interface Presenter extends BasePresenter<View> {
        public void onTabSelected(int tabId);
        public void onOperationInvoking();
        public boolean isInstalled();
    }
}
