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
    public static final String CN_FECHA = "fecha";
    public static final String CN_TIPO = "tipo";
    public static final String CN_PRIORIDAD = "prioridad";


    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CN_TITLE + " TEXT NOT NULL, "
            + CN_CONTENT + " TEXT, "
            + CN_FECHA + " TEXT, "
            + CN_TIPO + " TEXT, "
            + CN_PRIORIDAD + " INTEGER)";

    private DBHelper dbhelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {

        dbhelper = new DBHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String titulo, String contenido, String fecha, String tipo, int prioridad ){
        ContentValues valores = new ContentValues();
        valores.put(CN_TITLE,titulo);
        valores.put(CN_CONTENT,contenido);
        valores.put(CN_FECHA,fecha);
        valores.put(CN_TIPO,tipo);
        valores.put(CN_PRIORIDAD,prioridad);
        return valores;
    }

    public void insertarNota (String titulo, String contenido, String fecha, String tipo, int prioridad ){
        db.insert(TABLE_NAME, null, generarContentValues(titulo, contenido,fecha,tipo,prioridad));

    }

    public void actualizarNota (int id, String titulo, String contenido, String fecha, String tipo, int prioridad ) {
        db.update(TABLE_NAME,generarContentValues(titulo, contenido, fecha, tipo, prioridad),CN_ID+"="+id,null);

    }

    public void eliminarNota (String titulo) {
        String[] args = new String[]{titulo};
        db.delete(TABLE_NAME, CN_TITLE + "=?", args);
    }

    public Cursor listarnotas(){
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+ " ORDER BY "+CN_PRIORIDAD,null);
    }
}
