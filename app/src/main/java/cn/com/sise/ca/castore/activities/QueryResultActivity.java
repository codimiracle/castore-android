package cn.com.sise.ca.castore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.adapters.SimpleApplicationAdapter;
import cn.com.sise.ca.castore.server.ApplicationAction;
import cn.com.sise.ca.castore.server.SimpleServerActionCallback;
import cn.com.sise.ca.castore.server.som.ApplicationInfoBean;
import cn.com.sise.ca.castore.server.som.ApplicationQueryMessage;
import cn.com.sise.ca.castore.server.som.Message;

public class QueryResultActivity extends ServerActionActivity {

    public static final String KEYWORD_KEY = "keyword_key";
    public static final String QUERY_RESULT_ACTION = CAstoreApplication.PACKAGE_NAME + ".QueryResult";

    private List<ApplicationInfoBean> results;
    private SimpleApplicationAdapter adapter;

    private ListView queryResultList;
    private ApplicationAction.SearchAction action;
    private View loadingView;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);
        queryResultList = findViewById(R.id.query_result_list);
        queryResultList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (loadingView.getTop() < view.getHeight() && !loading) {
                    loadNextPage();
                    loading = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        loadingView = getLayoutInflater().inflate(R.layout.bottom_loading, queryResultList, false);
        queryResultList.addFooterView(loadingView);
        queryResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ApplicationDetailsActivity.APPLICATION_DETAILS_ACTION);
                intent.putExtra(ApplicationDetailsActivity.APPLICATION_ID, adapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        adapter = new SimpleApplicationAdapter();
        queryResultList.setAdapter(adapter);
        String keyword = getIntent().getStringExtra(KEYWORD_KEY);
        getSupportActionBar().setTitle(keyword + " 的搜索结果");
        if (keyword == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Unpredictable Error!");
            builder.setMessage("You must set keyword to search!!");
            builder.setPositiveButton(android.R.string.yes, null);
            builder.show();
            finish();
        }

        action = new ApplicationAction.SearchAction();
        action.setKeyword(keyword);
        action.setCallback(new SimpleServerActionCallback<ApplicationQueryMessage>() {
            @Override
            public void onSuccess(ApplicationQueryMessage message) {
                results = message.getResults();
                loading = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNextPage(results);
                    }
                });
            }

            @Override
            public void onFailure(Message message) {
                final String m = message.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(QueryResultActivity.this, m, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        addServerAction(action);
    }

    private void loadNextPage() {
        action.setPagerNumber(action.getPagerNumber() + 1);
        addServerAction(action);
    }

    private void onNextPage(List<ApplicationInfoBean> page) {
        adapter.addPageList(page);
        if (page.size() < 10) {
            queryResultList.removeFooterView(loadingView);
            loading = true;
        }
    }
}
