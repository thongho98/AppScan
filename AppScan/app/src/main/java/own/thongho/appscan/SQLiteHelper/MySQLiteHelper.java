package own.thongho.appscan.SQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
    // Tên cơ sở dữ liệu
    private static final String TEN_DATABASE = "Scanner";
    // Tên bảng
    public static final String BANG_HISTORY = "History";

    public static final String COT_ID = "_id";
    public static final String COT_CATEGORY = "_category";
    public static final String COT_CONTENT = "_content";
    public static final String COT_DATE = "_date";
    public static final String COL_IMG = "_img";
    public static final String COL_LIKE = "_like";

    private static final String TAO_BANG_HISTORY = ""
            + "create table " + BANG_HISTORY + " ( "
            + COT_ID + " integer primary key autoincrement ,"
            + COT_CATEGORY + " text not null, "
            + COT_CONTENT + " text not null, "
            + COT_DATE + " text not null, "
            + COL_IMG + " text not null, "
            + COL_LIKE + " text not null);";

    public MySQLiteHelper(Context context){
        super(context, TEN_DATABASE, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ BANG_HISTORY);
        onCreate(db);
    }
}
