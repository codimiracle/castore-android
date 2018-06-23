package cn.com.sise.ca.castore.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.activities.UserSessionActivity;
import cn.com.sise.ca.castore.adapters.CommentAdapter;
import cn.com.sise.ca.castore.server.CommentAction;
import cn.com.sise.ca.castore.server.ResourceAction;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.som.ApplicationMessage;
import cn.com.sise.ca.castore.server.som.CommentMessage;
import cn.com.sise.ca.castore.server.som.Message;

public class ApplicationCommentFragment extends ServerActionFragment {
    private ResourceAction.ImageCacheAction imageCacheAction;
    private CommentAction.CommentItAction commentItAction;
    private CommentAction.CommentsListActionCallback commentsListAction;

    private ApplicationMessage applicationMessage;
    private CommentMessage commentMessage;

    private ListView viewCommentList;
    private EditText viewCommentTheme;
    private EditText viewCommentContent;

    private CAstoreApplication application;

    private Button viewCommentSend;

    private CommentAdapter commentAdapter;
    private boolean loading = false;
    private boolean commenting = false;
    private View viewLoading;
    private View viewEmpty;


    public static ApplicationCommentFragment newInstance() {

        Bundle args = new Bundle();

        ApplicationCommentFragment fragment = new ApplicationCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageCacheAction = new ResourceAction.ImageCacheAction(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appliciation_comment, container, false);
        viewCommentList = view.findViewById(R.id.comment_list);
        viewCommentTheme = view.findViewById(R.id.comment_theme);
        viewCommentContent = view.findViewById(R.id.comment_content);
        viewCommentSend = view.findViewById(R.id.comment_btn_send);
        viewLoading = inflater.inflate(R.layout.bottom_loading, viewCommentList, false);
        viewEmpty = view.findViewById(R.id.list_empty_view);
        viewEmpty.findViewById(R.id.empty_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        viewCommentList.setEmptyView(viewEmpty);
        viewCommentList.addFooterView(viewLoading);
        commentAdapter = new CommentAdapter();
        viewCommentList.setAdapter(commentAdapter);
        viewCommentList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (viewLoading.getTop() < view.getHeight() && !loading) {
                    loadNextPage();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        viewCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!application.isLogged()) {
                    Toast.makeText(getContext(), "请先登录！！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserSessionActivity.SIGNIN_ACTION);
                    startActivityForResult(intent, 0x0001);
                    return;
                }
                if (!commenting) {
                    commenting = true;
                    commentItAction = new CommentAction.CommentItAction(getActivity());
                    commentItAction.setTargetId(applicationMessage.getAppInfo().getContentId());
                    commentItAction.addTopic(viewCommentTheme.getText().toString());
                    commentItAction.setComment(viewCommentContent.getText().toString());
                    commentItAction.setCallback(new SimpleServerActionCallback<Message>() {
                        @Override
                        public void onSuccess(final Message message) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    commenting = false;
                                    System.out.println(message.getMessage());
                                    Toast.makeText(getContext(), "评论成功！！", Toast.LENGTH_SHORT).show();
                                    refresh();
                                }
                            });

                        }

                        @Override
                        public void onFailure(Message message) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    commenting = false;
                                    Toast.makeText(getContext(), "评论失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    addServerAction(commentItAction);
                } else {
                    Toast.makeText(getContext(), "评论过快！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void refresh() {
        if (applicationMessage == null)
            return;
        commentAdapter.clear();
        commentsListAction = new CommentAction.CommentsListActionCallback(getActivity());
        commentsListAction.setTargetId(applicationMessage.getAppInfo().getContentId());
        commentsListAction.setPagerNumber(0);
        commentsListAction.setCallback(new SimpleServerActionCallback<CommentMessage>() {
            @Override
            public void onSuccess(CommentMessage message) {
                commentMessage = message;
                commentAdapter.addComment(message.getComments());
                loading = false;
                if (message.getComments().size() < 10) {
                    viewCommentList.removeFooterView(viewLoading);
                    loading = true;
                } else {
                    loading = false;
                }
            }

            @Override
            public void onFailure(Message message) {
                Toast.makeText(getContext(), "无法获取评论数据！！", Toast.LENGTH_SHORT).show();
            }
        });
        loadNextPage();
    }

    private void loadNextPage() {
        loading = true;
        commentsListAction.setPagerNumber(commentsListAction.getPagerNumber() + 1);
        addServerAction(commentsListAction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UserSessionActivity.SING_IN_REQUEST_CODE) {
            if (resultCode == UserSessionActivity.SIGN_IN_SUCCESS_CODE) {
                Toast.makeText(getContext(), "现在您可以进行评论啦！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "你没有权限进行评论！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        application = (CAstoreApplication) getActivity().getApplication();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setApplicationMessage(ApplicationMessage applicationMessage) {
        this.applicationMessage = applicationMessage;
        refresh();
    }
}
