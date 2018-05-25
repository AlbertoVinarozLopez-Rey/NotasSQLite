package es.ipo2.notassqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public DatabaseManager abrir() throws SQLException{
        db= dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar(){
        if (db != null) db.close();
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

    public void eliminarNota (String titulo){
        db.delete(TABLE_NAME,CN_TITLE+"=?",new String[]{titulo});
    }

    /*public Cursor cargarNotas (){
        String [] columnas = new String[]{CN_ID,CN_TITLE,CN_CONTENT};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);

    }*/

    /*public Cursor buscarNota (String titulo){
        String [] columnas = new String[]{CN_ID,CN_TITLE,CN_CONTENT};
        return db.query(TABLE_NAME,columnas,CN_TITLE+"=?",new String[]{titulo},null,null,null);
    }*/

    /*public ArrayList<String> obtenerNotas (){
        ArrayList<String> listaNotas = new ArrayList<String>();
        String sql = "SELECT * FROM "+TABLE_NAME;
        Cursor registros = db.rawQuery(sql,null);
        if (registros.moveToFirst()){
            do{
                listaNotas.add(registros.getString(1);
            }while (registros.moveToNext());
        }
        return listaNotas;
        }*/

    public Cursor listarnotas(){
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+ " ORDER BY "+CN_PRIORIDAD,null);
    }
}
