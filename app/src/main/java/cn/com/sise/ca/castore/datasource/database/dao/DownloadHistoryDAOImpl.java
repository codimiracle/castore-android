package cn.com.sise.ca.castore.datasource.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.datasource.database.entities.DownloadHistory;
import cn.com.sise.ca.castore.datasource.database.helpers.DownloadHistoryHelper;

public class DownloadHistoryDAOImpl implements DownloadHistoryDAO {
    private DownloadHistoryHelper downloadHistoryHelper;

    public DownloadHistoryHelper getDownloadHistoryHelper() {
        return downloadHistoryHelper;
    }

    public void setDownloadHistoryHelper(DownloadHistoryHelper downloadHistoryHelper) {
        this.downloadHistoryHelper = downloadHistoryHelper;
    }

    @Override
    public ContentValues convertToContentValues(DownloadHistory entity) {
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        ContentValues values = new ContentValues();
        values.put(DownloadHistoryHelper.FIELD_APPID, entity.getAppId());
        values.put(DownloadHistoryHelper.FIELD_DOWNLOADTIME, format.format(entity.getDownloadTime()));
        return values;
    }

    @Override
    public long insert(DownloadHistory entity) {
        SQLiteDatabase database = downloadHistoryHelper.getWritableDatabase();
        long id = database.insert(DownloadHistoryHelper.TABLE_NAME, null, convertToContentValues(entity));
        database.close();
        return id;
    }

    @Override
    public int update(DownloadHistory entity) {
        SQLiteDatabase database = downloadHistoryHelper.getWritableDatabase();
        int effective = database.update(DownloadHistoryHelper.TABLE_NAME, convertToContentValues(entity), "_id = ?", new String[]{"" + entity.getId()});
        database.close();
        return effective;
    }

    @Override
    public int delete(DownloadHistory entity) {
        SQLiteDatabase database = downloadHistoryHelper.getWritableDatabase();
        int effective = database.delete(DownloadHistoryHelper.TABLE_NAME, "_id = ?", new String[]{"" + entity.getId()});
        database.close();
        return effective;
    }

    @Override
    public List<DownloadHistory> query() {
        SQLiteDatabase database = downloadHistoryHelper.getReadableDatabase();
        List<DownloadHistory> list = new ArrayList<>();
        Cursor cursor = database.query(DownloadHistoryHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            DownloadHistory downloadHistory = new DownloadHistory();
            downloadHistory.setId(cursor.getLong(0));
            downloadHistory.setAppId(cursor.getInt(1));
            downloadHistory.setDownloadTime(Date.valueOf(cursor.getString(2)));
            list.add(downloadHistory);
        }
        cursor.close();
        return list;
    }
}
