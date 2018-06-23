package cn.com.sise.ca.castore.datasource.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.datasource.database.entities.SearchHistory;
import cn.com.sise.ca.castore.datasource.database.helpers.SearchHistoryHelper;

public class SearchHistoryDAOImpl implements SearchHistoryDAO {
    private SearchHistoryHelper searchHistoryHelper;

    public SearchHistoryHelper getSearchHistoryHelper() {
        return searchHistoryHelper;
    }

    public void setSearchHistoryHelper(SearchHistoryHelper searchHistoryHelper) {
        this.searchHistoryHelper = searchHistoryHelper;
    }

    @Override
    public ContentValues convertToContentValues(SearchHistory entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchHistoryHelper.FIELD_KEYWORD, entity.getKeyword());
        return contentValues;
    }

    @Override
    public long insert(SearchHistory entity) {
        SQLiteDatabase database = searchHistoryHelper.getWritableDatabase();
        long id = database.insert(SearchHistoryHelper.TABLE_NAME, null, convertToContentValues(entity));
        database.close();
        return id;
    }

    @Override
    public int update(SearchHistory entity) {
        return 0;
    }

    @Override
    public int delete(SearchHistory entity) {
        SQLiteDatabase database = searchHistoryHelper.getWritableDatabase();
        int effective = database.delete(SearchHistoryHelper.TABLE_NAME, "_id = ?", new String[]{"" + entity.getId()});
        database.close();
        return effective;
    }
    private List<SearchHistory> convertToList(Cursor cursor) {
        List<SearchHistory> list = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setId(cursor.getLong(0));
            searchHistory.setKeyword(cursor.getString(1));
            list.add(searchHistory);
        }
        return list;
    }

    @Override
    public List<SearchHistory> query() {
        SQLiteDatabase database = searchHistoryHelper.getReadableDatabase();
        Cursor cursor = database.query(SearchHistoryHelper.TABLE_NAME, null, null, null, null, null, null);
        List<SearchHistory> list = convertToList(cursor);
        cursor.close();
        return list;
    }

    @Override
    public List<SearchHistory> queryByKeyword(String keyword) {
        SQLiteDatabase database = searchHistoryHelper.getReadableDatabase();
        Cursor cursor = database.query(SearchHistoryHelper.TABLE_NAME, null, "keyword like ?", new String[]{keyword}, null, null, null);
        List<SearchHistory> list = convertToList(cursor);
        cursor.close();
        return list;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
