package es.ipo2.notassqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

public class EditarNota extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtContenido;
    private Spinner spTipo;
    private EditText txtFecha;
    private Spinner spPrioridad;
    private DatabaseManager db;
    private int idNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);
        db = new DatabaseManager(this);


        getSupportActionBar().setTitle("Editar nota");
        txtTitulo = findViewById(R.id.txtTitulo);
        txtContenido = findViewById(R.id.txtDescripcion);
        txtFecha= findViewById(R.id.txtFecha);
        spTipo=findViewById(R.id.spTipo);
        spPrioridad=findViewById(R.id.spPrioridad);

        String[] opcionesTipo = {"Familiar","Deporte","Amigos","Personal","Trabajo","Estudios"};
        spTipo.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, opcionesTipo));

        String[] opcionesPrioridad = {"Urgente","Alta","Media","Baja"};
        spPrioridad.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, opcionesPrioridad));

        Bundle bundle=getIntent().getExtras();
        idNota=bundle.getInt("idNota");
        txtTitulo.setText(bundle.getString("titulo"));
        txtContenido.setText(bundle.getString("contenido"));
        spPrioridad.setSelection(bundle.getInt("prioridad")-1);
        txtFecha.setText(bundle.getString("fecha"));

        comprobarTipo(bundle);
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

    public void oyente_btnVolver(View view){
        finish();
    }

    public void oyente_btnGuardarCambios(View view){
        int prioridad=0;
        if(spPrioridad.getSelectedItem().toString().equals("Urgente")){
            prioridad=1;
        }else if (spPrioridad.getSelectedItem().toString().equals("Alta")){
            prioridad=2;
        }else if(spPrioridad.getSelectedItem().toString().equals("Media")){
            prioridad = 3;
        }else if(spPrioridad.getSelectedItem().toString().equals("Baja")){
            prioridad = 4;
        }
        db.actualizarNota(idNota,txtTitulo.getText().toString(),txtContenido.getText().toString(),txtFecha.getText().toString(),
                spTipo.getSelectedItem().toString(),prioridad);

        Toast notificacion;
        notificacion = Toast.makeText(this, "La tarea "+txtTitulo.getText().toString()+ " se ha actualizado " +
                "correctamente",Toast.LENGTH_LONG);
        notificacion.show();
        Intent resultado = new Intent();
        setResult(RESULT_OK, resultado);
        finish();
    }
}
