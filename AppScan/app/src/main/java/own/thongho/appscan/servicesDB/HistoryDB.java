package own.thongho.appscan.servicesDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import own.thongho.appscan.SQLiteHelper.MySQLiteHelper;
import own.thongho.appscan.models.History;

public class HistoryDB {
    private SQLiteDatabase database;
    private MySQLiteHelper myHelper;

    public HistoryDB(Context context) {
        myHelper = new MySQLiteHelper(context);
        try {
            database = myHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            database = myHelper.getReadableDatabase();
        }

    }

    public void close() {
        myHelper.close();
    }

    public Cursor getAllData() {
        String query = "Select * from " + MySQLiteHelper.BANG_HISTORY + " order by "+ MySQLiteHelper.COT_ID + " DESC";
        Cursor cursor = null;
        cursor = database.rawQuery(query, null);
        return cursor;
    }

    public long addHistory(History history) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COT_CATEGORY, history.getCategory());
        values.put(MySQLiteHelper.COT_CONTENT, history.getContent());
        values.put(MySQLiteHelper.COT_DATE, history.getDate());
        values.put(MySQLiteHelper.COL_IMG, history.getImg());
        values.put(MySQLiteHelper.COL_LIKE, history.getLike());
        return database.insert(MySQLiteHelper.BANG_HISTORY, null, values);
    }

    public void updateHistory(History history) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COT_CATEGORY, history.getCategory());
        values.put(MySQLiteHelper.COT_CONTENT, history.getContent());
        values.put(MySQLiteHelper.COT_DATE, history.getDate());
        values.put(MySQLiteHelper.COL_IMG, history.getImg());
        values.put(MySQLiteHelper.COL_LIKE, 1);
        database.update(MySQLiteHelper.BANG_HISTORY, values,
                MySQLiteHelper.COT_ID + " = " + history.getId(), null);
    }

    public void deleteHistory(int id) {
        database.delete(MySQLiteHelper.BANG_HISTORY, MySQLiteHelper.COT_ID + " = " + "'" +
                id + "'", null);
    }

    public Cursor getAllDataFavorite() {
        String query = "Select * from " + MySQLiteHelper.BANG_HISTORY + " where _like = 1";
        Cursor cursor = null;
        cursor = database.rawQuery(query, null);
        return cursor;
    }

    public void updateHistoryUnlike(History history) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COT_CATEGORY, history.getCategory());
        values.put(MySQLiteHelper.COT_CONTENT, history.getContent());
        values.put(MySQLiteHelper.COT_DATE, history.getDate());
        values.put(MySQLiteHelper.COL_IMG, history.getImg());
        values.put(MySQLiteHelper.COL_LIKE, 0);
        database.update(MySQLiteHelper.BANG_HISTORY, values,
                MySQLiteHelper.COT_ID + " = " + history.getId(), null);
    }
}
