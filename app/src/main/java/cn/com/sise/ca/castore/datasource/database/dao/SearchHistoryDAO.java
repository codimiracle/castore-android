package cn.com.sise.ca.castore.datasource.database.dao;

import java.util.List;

import cn.com.sise.ca.castore.datasource.database.entities.SearchHistory;

public interface SearchHistoryDAO extends DataAccessObject<SearchHistory> {
    public List<SearchHistory> queryByKeyword(String keyword);
    public int deleteAll();
}
