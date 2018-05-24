package es.ipo2.notassqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;
    private Cursor cursor;
    private ListView lstNotas;
    private ArrayList<Nota> notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.border_color);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), NuevaNota.class));
            }
        });

        lstNotas = (ListView) findViewById(R.id.lstNotas);

        notas = cargarNotas();
        MiClaseAdaptador adaptador = new MiClaseAdaptador(this, notas);
        lstNotas.setAdapter(adaptador);
    }

    public ArrayList<Nota> cargarNotas(){
        ArrayList<Nota> listaNotas = new ArrayList<Nota>();
        db = new DatabaseManager(this);
        db.abrir();
        cursor = db.listarnotas();

        if(cursor.moveToFirst()){
            do{
                Nota nota = new Nota(cursor.getString(1), cursor.getString(2));
                listaNotas.add(nota);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.cerrar();
        return listaNotas;
    }

    /*@Override
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
                adaptador.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

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
}
