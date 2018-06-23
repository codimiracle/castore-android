package cn.com.sise.ca.castore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import cn.com.sise.ca.castore.R;
import cn.com.sise.ca.castore.datasource.database.dao.SearchHistoryDAO;
import cn.com.sise.ca.castore.datasource.database.entities.SearchHistory;

public class SearchHistoryAdapter extends RecyclerView.Adapter {
    private List<SearchHistory> keywords;
    private SearchHistoryDAO searchHistoryDAO;
    private OnItemClickListener onItemClickListener;

    public void setSearchHistoryDAO(SearchHistoryDAO searchHistoryDAO) {
        this.searchHistoryDAO = searchHistoryDAO;
        refresh();
    }

    public SearchHistoryDAO getSearchHistoryDAO() {
        return searchHistoryDAO;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void refresh() {
        keywords = this.searchHistoryDAO.query();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SearchHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_item, parent, false);
        return new SearchHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SearchHistoryHolder keywordViewHolder = (SearchHistoryHolder) holder;
        keywordViewHolder.setSearchHistory(keywords.get(position));
        keywordViewHolder.setCallback(new SearchHistoryHolder.Callback() {
            @Override
            public void onClose() {
                searchHistoryDAO.delete(keywords.get(keywordViewHolder.getAdapterPosition()));
                refresh();
            }
        });
        keywordViewHolder.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public static class SearchHistoryHolder extends RecyclerView.ViewHolder {
        private TextView keyword;
        private ImageButton close;
        private Callback callback;
        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public OnItemClickListener getOnItemClickListener() {
            return onItemClickListener;
        }

        private SearchHistory searchHistory;

        public SearchHistoryHolder(View itemView) {
            super(itemView);
            keyword = itemView.findViewById(R.id.keyword_item_keyword);
            close = itemView.findViewById(R.id.keyword_item_close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onClose();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), searchHistory);
                }
            });
        }

        public SearchHistory getSearchHistory() {
            return searchHistory;
        }

        public void setSearchHistory(SearchHistory searchHistory) {
            this.searchHistory = searchHistory;
            keyword.setText(searchHistory.getKeyword());
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        public Callback getCallback() {
            return callback;
        }

        public interface Callback {
            public void onClose();
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, SearchHistory history);
    }
}
