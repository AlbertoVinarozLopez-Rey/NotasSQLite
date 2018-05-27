package es.ipo2.notassqlite;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class VerNota extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtContenido;
    private TextView txtFecha;
    private TextView txtTipo;
    private TextView txtPrioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);
        getSupportActionBar().setTitle("Ver nota");

        txtTitulo = findViewById(R.id.txtTitulo);
        txtContenido = findViewById(R.id.txtContenido);
        txtFecha= findViewById(R.id.txtFecha);
        txtPrioridad = findViewById(R.id.txtPrioridad);
        txtTipo= findViewById(R.id.txtTipo);

        Bundle bundle=getIntent().getExtras();

        txtTitulo.setText(bundle.getString("titulo"));
        txtContenido.setText(bundle.getString("contenido"));
        txtFecha.setText(bundle.getString("fecha"));
        txtTipo.setText(bundle.getString("tipo"));
        comprobarPrioridad(bundle);
    }

    public void comprobarPrioridad (Bundle bundle){

        if(bundle.getInt("prioridad")==1){
            txtPrioridad.setText("Urgente");
        }else if(bundle.getInt("prioridad")==2){
            txtPrioridad.setText("Alta");
        }else if(bundle.getInt("prioridad")==3){
            txtPrioridad.setText("Media");
        }else if(bundle.getInt("prioridad")==4){
            txtPrioridad.setText("Baja");

        }
    }

    public void oyente_btnVolver(View view){
        finish();
    }
}
