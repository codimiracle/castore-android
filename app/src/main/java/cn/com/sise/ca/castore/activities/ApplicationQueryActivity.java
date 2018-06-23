package cn.com.sise.ca.castore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.util.List;

import cn.com.sise.ca.castore.CAstoreApplication;
import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.adapters.SearchHistoryAdapter;
import cn.com.sise.ca.castore.datasource.database.dao.SearchHistoryDAO;
import cn.com.sise.ca.castore.datasource.database.dao.SearchHistoryDAOImpl;
import cn.com.sise.ca.castore.datasource.database.entities.SearchHistory;
import cn.com.sise.ca.castore.datasource.database.helpers.SearchHistoryHelper;

public class ApplicationQueryActivity extends BaseActivity {
    public static final String TAG = "APP QUERY";

    public static final String APPLICATION_QUERY_ACTION = CAstoreApplication.PACKAGE_NAME + ".ApplicationQuery";

    private AutoCompleteTextView autoCompleteTextView;
    private RecyclerView searchHistoryRecyclerView;
    private LinearLayout searchHistoryContainer;
    private SearchHistoryHelper searchHistoryHelper;
    private SearchHistoryDAO searchHistoryDAO;
    //搜索到的搜索历史：
    private List<SearchHistory> searchHistoryList;
    //搜索历史 RecycleViewAdapter
    private SearchHistoryAdapter searchHistoryAdapter;

    private void init() {
        searchHistoryAdapter.refresh();
        if (searchHistoryAdapter.getItemCount() == 0) {
            searchHistoryContainer.setVisibility(View.GONE);
        } else {
            searchHistoryContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_query);
        searchHistoryContainer = findViewById(R.id.search_history_container);
        searchHistoryRecyclerView = findViewById(R.id.history_list);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        searchHistoryRecyclerView.setLayoutManager(manager);
        autoCompleteTextView = findViewById(R.id.search_keyword);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchHistoryList = searchHistoryDAO.queryByKeyword(s.toString());
            }
        });
        searchHistoryHelper = new SearchHistoryHelper(this);
        SearchHistoryDAOImpl s = new SearchHistoryDAOImpl();
        s.setSearchHistoryHelper(searchHistoryHelper);
        searchHistoryDAO = s;
        searchHistoryAdapter = new SearchHistoryAdapter();
        searchHistoryAdapter.setSearchHistoryDAO(searchHistoryDAO);
        searchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, SearchHistory history) {
                autoCompleteTextView.getEditableText().clear();
                autoCompleteTextView.getEditableText().append(history.getKeyword());
            }
        });
        searchHistoryRecyclerView.setAdapter(searchHistoryAdapter);
        init();
    }


    public void onSearch(View view) {
        String keyword = autoCompleteTextView.getText().toString();
        if (searchHistoryList != null && searchHistoryList.isEmpty() && !keyword.equals("")) {
            SearchHistory history = new SearchHistory();
            history.setKeyword(keyword);
            searchHistoryDAO.insert(history);
            searchHistoryList.add(history);
            init();
        }
        Intent intent = new Intent(QueryResultActivity.QUERY_RESULT_ACTION);
        intent.putExtra(QueryResultActivity.KEYWORD_KEY, keyword);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchHistoryHelper.close();
    }
}
