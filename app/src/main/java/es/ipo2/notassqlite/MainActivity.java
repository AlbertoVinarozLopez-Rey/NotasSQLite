package es.ipo2.notassqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

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
        // si ves esto acuerdate de meterte en Device File Explorer, eliminar la base de datos y su copia
        // y de hacer un insert antes de ejecutar, o crear la nota tu mismo, que ya esta implementado
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.border_color);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),NuevaNota.class);
                startActivityForResult(i, NEW_NOTE_REQUEST);
            }
        });


        cargarListaNotas();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_NOTE_REQUEST){
            if (resultCode == RESULT_OK){
                cargarListaNotas();

            }
        }
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
                Nota nota = new Nota(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getInt(5));
                listaNotas.add(nota);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.cerrar();
        return listaNotas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item  = menu.findItem(R.id.menuSearch);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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

    public boolean onContextItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.verDetalles:
                Intent i = new Intent(this, DetallesNota.class);
                i.putExtra("titulo", notas.get(notaSeleccionada).getTitulo());
                i.putExtra("contenido", notas.get(notaSeleccionada).getContenido());
                i.putExtra("fecha",notas.get(notaSeleccionada).getFecha());
                i.putExtra("tipo",notas.get(notaSeleccionada).getTipo());
                i.putExtra("prioridad",notas.get(notaSeleccionada).getPrioridad());
                startActivity(i);

                break;

            case R.id.borrarNota:

                db.eliminarNota(notas.get(notaSeleccionada).getTitulo());
                notas.remove(notaSeleccionada);
                adapter.notifyDataSetChanged();

                break;
            case R.id.editarNota:
                break;
        }
        return true;
    }
}
