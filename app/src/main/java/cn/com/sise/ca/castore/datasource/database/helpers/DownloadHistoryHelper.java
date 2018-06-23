package cn.com.sise.ca.castore.datasource.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DownloadHistoryHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "download";
    public static final String FIELD_APPID = "appId";
    public static final String FIELD_DOWNLOADTIME = "downloadTime";
    private static final String DDL = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, appId INTEGER, downloadTime DATETIME) ";

    public DownloadHistoryHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
