package cn.com.sise.ca.castore.datasource.database.dao;

import android.content.ContentValues;

import java.util.List;

import cn.com.sise.ca.castore.datasource.database.entities.Entity;

public interface DataAccessObject<T extends Entity> {
    public ContentValues convertToContentValues(T entity);
    public long insert(T entity);
    public int update(T entity);
    public int delete(T entity);
    public List<T> query();
}
