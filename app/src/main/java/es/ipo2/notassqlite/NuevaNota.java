package es.ipo2.notassqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevaNota extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private TextView lblTitulo;
    private TextView lblDescripcion;
    private Button btnGuardar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        lblTitulo = findViewById(R.id.lblTituloN);
        lblDescripcion = findViewById(R.id.lblDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        txtTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtTitulo.equals("")){
                    btnGuardar.setEnabled(false);
                }else{
                    btnGuardar.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void oyente_btnVolver (View view){
        finish();

    }
}
