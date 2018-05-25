package es.ipo2.notassqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alber on 24/05/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME= "notas.sqlite";
    private static final int DB_SCHEMA_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE);
    }
}
