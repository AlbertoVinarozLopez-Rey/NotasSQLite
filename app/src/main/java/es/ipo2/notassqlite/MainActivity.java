package es.ipo2.notassqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;
    private Cursor cursor;
    private ListView lstNotas;
    private ArrayList<Nota> notas;
    private MiClaseAdaptador adapter;
    private int notaSeleccionada;
    private static final int NEW_NOTE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_new_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaNota();
            }
        });

        cargarListaNotas();

        lstNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), DetallesNota.class);
                i.putExtra("titulo", notas.get(notaSeleccionada).getTitulo());
                i.putExtra("contenido", notas.get(notaSeleccionada).getContenido());
                i.putExtra("fecha",notas.get(notaSeleccionada).getFecha());
                i.putExtra("tipo",notas.get(notaSeleccionada).getTipo());
                i.putExtra("prioridad",notas.get(notaSeleccionada).getPrioridad());
                i.putExtra("editar", false);
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_NOTE_REQUEST){
            if (resultCode == RESULT_OK){
                cargarListaNotas();
            }
        }
    }

    public void nuevaNota(){
        Intent i = new Intent(this,NuevaNota.class);
        startActivityForResult(i, NEW_NOTE_REQUEST);
    }

    public void cargarListaNotas(){
        lstNotas = (ListView) findViewById(R.id.lstNotas);
        notas = cargarNotas();
        adapter = new MiClaseAdaptador(this, notas);
        adapter.notifyDataSetChanged();
        lstNotas.setAdapter(adapter);
        registerForContextMenu(lstNotas);

    }

    public ArrayList<Nota> cargarNotas(){
        ArrayList<Nota> listaNotas = new ArrayList<Nota>();
        db = new DatabaseManager(this);
        cursor = db.listarnotas();

        if(cursor.moveToFirst()){
            do{
                Nota nota = new Nota(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getInt(5));
                listaNotas.add(nota);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNotas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item  = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setQueryHint("Buscar notas...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filtrar(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAbout:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acerca de...");
                builder.setMessage("Aplicación creada por Jesús Martínez Manrique y Alberto Vinaroz López-Rey, alumnos de la Escuela " +
                        "Superior de Informática, para la asignatura Interacción Persona-Ordenador II");
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                break;
            case R.id.menuAddNote:
                nuevaNota();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        notaSeleccionada = info.position;


    }

    public void verNota(){
        Intent i = new Intent(this, DetallesNota.class);
        i.putExtra("titulo", notas.get(notaSeleccionada).getTitulo());
        i.putExtra("contenido", notas.get(notaSeleccionada).getContenido());
        i.putExtra("fecha",notas.get(notaSeleccionada).getFecha());
        i.putExtra("tipo",notas.get(notaSeleccionada).getTipo());
        i.putExtra("prioridad",notas.get(notaSeleccionada).getPrioridad());
        i.putExtra("editar", false);
        startActivity(i);
    }

    public boolean onContextItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.verDetalles:
                verNota();
                break;

            case R.id.borrarNota:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("¿Eliminar nota " +
                        "'"+notas.get(notaSeleccionada).getTitulo()+"'?");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarNota(notas.get(notaSeleccionada).getTitulo());
                        notas.remove(notaSeleccionada);
                        adapter.notifyDataSetChanged();
                    } });

                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    } });
                adb.show();
                break;

            case R.id.editarNota:
                Intent i2 = new Intent(this, DetallesNota.class);
                i2.putExtra("idNota",notas.get(notaSeleccionada).getIdNota());
                i2.putExtra("titulo", notas.get(notaSeleccionada).getTitulo());
                i2.putExtra("contenido", notas.get(notaSeleccionada).getContenido());
                i2.putExtra("fecha",notas.get(notaSeleccionada).getFecha());
                i2.putExtra("tipo",notas.get(notaSeleccionada).getTipo());
                i2.putExtra("prioridad",notas.get(notaSeleccionada).getPrioridad());
                i2.putExtra("editar", true);
                startActivityForResult(i2, NEW_NOTE_REQUEST);
                break;
        }
        return true;
    }
}
