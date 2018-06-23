package cn.com.sise.ca.castore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.R;

public class KeywordAdapter extends RecyclerView.Adapter {
    private List<String> keywords = new ArrayList<>();

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @NonNull
    @Override
    public KeywordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_item, parent, false);
        return new KeywordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KeywordHolder keywordViewHolder = (KeywordHolder) holder;
        keywordViewHolder.setKeyword(keywords.get(position));
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public static class KeywordHolder extends RecyclerView.ViewHolder {
        private TextView keyword;
        private Callback callback;

        public KeywordHolder(View itemView) {
            super(itemView);
            keyword = itemView.findViewById(R.id.keyword_item_keyword);
        }

        public void setKeyword(String keyword) {
            this.keyword.setText(keyword);
        }

        public String getKeyword() {
            return "" + keyword.getText();
        }

        public interface Callback {
            public void onClose();
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
}
