package es.ipo2.notassqlite;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class DetallesNota extends Activity {

    private EditText txtTitulo;
    private EditText txtContenido;
    private Spinner spTipo;
    private EditText txtFecha;
    private Spinner spPrioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_nota);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtContenido = findViewById(R.id.txtDescripcion);
        txtFecha= findViewById(R.id.txtFecha);
        spTipo=findViewById(R.id.spTipo);
        spPrioridad=findViewById(R.id.spPrioridad);

        String[] opcionesTipo = {"Familiar","Deporte","Amigos","Personal","Trabajo","Estudios"};
        spTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesTipo));

        String[] opcionesPrioridad = {"Urgente","Alta","Media","Baja"};
        spPrioridad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesPrioridad));

        Bundle bundle=getIntent().getExtras();
        txtTitulo.setText(bundle.getString("titulo"));
        txtContenido.setText(bundle.getString("contenido"));
        spPrioridad.setSelection(bundle.getInt("prioridad")-1);
        comprobarTipo(bundle);
        txtFecha.setText(bundle.getString("fecha"));

        spTipo.setEnabled(false);
        spPrioridad.setEnabled(false);
    }

    public void comprobarTipo(Bundle bundle){

        if(bundle.getString("tipo").equals("Familiar")){
            spTipo.setSelection(0);
        }else if(bundle.getString("tipo").equals("Deporte")){
            spTipo.setSelection(1);
        }else if(bundle.getString("tipo").equals("Amigos")){
            spTipo.setSelection(2);
        }else if(bundle.getString("tipo").equals("Personal")){
            spTipo.setSelection(3);
        }else if(bundle.getString("tipo").equals("Trabajo")){
            spTipo.setSelection(4);
        }else if(bundle.getString("tipo").equals("Estudios")){
            spTipo.setSelection(5);
        }


    }
}
