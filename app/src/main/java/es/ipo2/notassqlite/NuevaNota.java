package es.ipo2.notassqlite;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaNota extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private TextView lblTitulo;
    private TextView lblDescripcion;
    private Button btnGuardar;
    private Spinner spTipo;
    private Spinner spPrioridad;
    private TextView lblTipo;
    private TextView lblFecha;
    private EditText txtFecha;
    private Menu myMenu;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DatabaseManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        lblTitulo = findViewById(R.id.lblTituloN);
        lblDescripcion = findViewById(R.id.lblDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        lblFecha= findViewById(R.id.lblFecha);
        lblTipo = findViewById(R.id.lblTipo);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        txtFecha = findViewById(R.id.txtFecha);
        spPrioridad = findViewById(R.id.spPrioridad);

        String[] opcionesTipo = {"Familiar","Deporte","Amigos","Personal","Trabajo","Estudios"};
        spTipo.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, opcionesTipo));

        String[] opcionesPrioridad = {"Urgente","Alta","Media","Baja"};
        spPrioridad.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, opcionesPrioridad));

        txtTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtTitulo.getText().toString().length()==0){
                    btnGuardar.setEnabled(false);
                  //  myMenu.getItem(R.id.menuGuardarNota).setEnabled(false);
                }else{
                    btnGuardar.setEnabled(true);
                  //  myMenu.getItem(R.id.menuGuardarNota).setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void oyente_btnVolver (View view) {
        finish();
    }

    public void oyente_btnGuardar(View view){
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
        db.insertarNota(txtTitulo.getText().toString(),txtDescripcion.getText().toString(),
                txtFecha.getText().toString(),spTipo.getSelectedItem().toString(),prioridad);

        Toast notificacion;
        notificacion = Toast.makeText(this, "La tarea "+txtTitulo.getText().toString()+ " se ha creado " +
                        "correctamente",Toast.LENGTH_LONG);
        notificacion.show();
        Intent resultado = new Intent();
        setResult(RESULT_OK, resultado);
        finish();
    }
}
