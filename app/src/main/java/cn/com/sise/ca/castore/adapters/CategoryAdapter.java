package cn.com.sise.ca.castore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.CategoryInfoBean;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryInfoBean> categories;

    public List<CategoryInfoBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryInfoBean> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public CategoryInfoBean getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static int[] subtags = new int[]{R.id.category_subtag_1, R.id.category_subtag_2, R.id.category_subtag_3, R.id.category_subtag_4, R.id.category_subtag_5, R.id.category_subtag_6};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);


        }
        return itemView;
    }
}
