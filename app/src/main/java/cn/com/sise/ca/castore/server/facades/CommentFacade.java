package cn.com.sise.ca.castore.server.facades;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.server.som.CommentInfoBean;

public class CommentFacade implements Facade {
    private View view;

    private ImageView viewIcon;
    private TextView viewTopic;
    private TextView viewContent;
    private TextView viewVote;

    private CommentInfoBean commentInfoBean;

    public CommentInfoBean getCommentInfoBean() {
        return commentInfoBean;
    }

    public void setCommentInfoBean(CommentInfoBean commentInfoBean) {
        this.commentInfoBean = commentInfoBean;
        viewTopic.setText(commentInfoBean.getTitle());
        viewContent.setText(commentInfoBean.getContent());
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        viewIcon = view.findViewById(R.id.som_comment_user_icon);
        viewTopic = view.findViewById(R.id.som_comment_title);
        viewContent = view.findViewById(R.id.som_comment_content);
        viewVote = view.findViewById(R.id.som_comment_vote);
    }
}
