package cn.com.sise.ca.castore.datasource.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchHistoryHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "search_info";
    public static final String FIELD_KEYWORD = "keyword";

    private static final String TABLE_DDL = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, keyword TEXT);";

    private static final String DATABASE_NAME = "application.db";
    private static final int DATABASE_VERSION = 1;

    public SearchHistoryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
