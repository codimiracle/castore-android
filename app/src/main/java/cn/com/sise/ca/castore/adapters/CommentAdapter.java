package cn.com.sise.ca.castore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.facades.CommentFacade;
import cn.com.sise.ca.castore.server.som.CommentInfoBean;

public class CommentAdapter extends BaseAdapter {
    List<CommentInfoBean> comments;
    public CommentAdapter() {
        comments = new ArrayList<>();
    }
    public void addComment(List<CommentInfoBean> list) {
        for (CommentInfoBean bean : list) {
            comments.add(bean);
            notifyDataSetChanged();
        }
    }
    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public CommentInfoBean getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CommentFacade facade;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.som_comment_item, parent, false);
            facade = new CommentFacade();
            facade.setView(view);
        } else {
            facade = (CommentFacade) convertView.getTag();
        }
        facade.setCommentInfoBean(getItem(position));
        view.setTag(facade);
        return view;
    }
}
