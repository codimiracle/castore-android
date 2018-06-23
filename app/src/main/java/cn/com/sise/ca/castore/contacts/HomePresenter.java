package cn.com.sise.ca.castore.contacts;

import cn.com.sise.ca.castore.contacts.abstracts.HomeContact;

public class HomePresenter implements HomeContact.Presenter {
    private HomeContact.View view;
    @Override
    public void setView(HomeContact.View view) {
        this.view = view;
    }

    @Override
    public void onNextPageLoad() {

    }
}
