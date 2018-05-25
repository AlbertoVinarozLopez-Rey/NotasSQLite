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
        // si ves esto acuerdate de meterte en Device File Explorer, eliminar la base de datos y su copia
        // y de hacer un insert antes de ejecutar, o crear la nota tu mismo, que ya esta implementado
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lstNotas = (ListView) findViewById(R.id.lstNotas);

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

        lstNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetallesNota.class);
                i.putExtra("titulo", notas.get(position).getTitulo());
                i.putExtra("contenido", notas.get(position).getContenido());
                i.putExtra("fecha",notas.get(position).getFecha());
                i.putExtra("tipo",notas.get(position).getTipo());
                i.putExtra("prioridad",notas.get(position).getPrioridad());
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

    public void cargarListaNotas(){
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
            Toast notificacion = Toast.makeText(this, "Esta aplicación ha sido realizada por: Jesús Martínez Manrique y " +
                    "Alberto Vinaroz López-Rey",Toast.LENGTH_LONG);
            notificacion.show();
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
                i.putExtra("editar", false);
                startActivity(i);
                break;

            case R.id.borrarNota:
                db.eliminarNota(notas.get(notaSeleccionada).getTitulo());
                notas.remove(notaSeleccionada);
                adapter.notifyDataSetChanged();
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
