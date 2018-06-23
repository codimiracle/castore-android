package cn.com.sise.ca.castore.server.som;

import java.util.List;

public class CategoryMessage {
    private List<CategoryInfoBean> categories;

    public List<CategoryInfoBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryInfoBean> categories) {
        this.categories = categories;
    }
}
