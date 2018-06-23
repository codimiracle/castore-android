package cn.com.sise.ca.castore.contacts.abstracts;

import cn.com.sise.ca.castore.framework.BasePresenter;
import cn.com.sise.ca.castore.framework.BaseView;

public interface UserContact {
    public interface View extends BaseView {}

    public interface Presenter extends BasePresenter<View> {

    }
}
