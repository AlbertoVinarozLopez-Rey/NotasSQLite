package es.ipo2.notassqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alber on 24/05/2018.
 */

public class DatabaseManager {

    public static final String TABLE_NAME = "notas";
    public static final String CN_ID = "id";
    public static final String CN_TITLE = "titulo";
    public static final String CN_CONTENT = "contenido";


    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CN_TITLE + " TEXT NOT NULL, "
            + CN_CONTENT + " TEXT)";

    private DBHelper helper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        helper = new DBHelper(context);
        db= helper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String titulo, String contenido ){
        ContentValues valores = new ContentValues();
        valores.put(CN_TITLE,titulo);
        valores.put(CN_CONTENT,contenido);

        return valores;
    }

    public void insertar (String titulo, String contenido){
        db.insert(TABLE_NAME, null, generarContentValues(titulo, contenido));

    }

    public void eliminar (String titulo){
        db.delete(TABLE_NAME,CN_TITLE+"=?",new String[]{titulo});
    }

    public Cursor cargarNotas (){
        String [] columnas = new String[]{CN_ID,CN_TITLE,CN_CONTENT};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);

    }

    public Cursor buscarNota (String titulo){
        String [] columnas = new String[]{CN_ID,CN_TITLE,CN_CONTENT};
        return db.query(TABLE_NAME,columnas,CN_TITLE+"=?",new String[]{titulo},null,null,null);
    }
}
