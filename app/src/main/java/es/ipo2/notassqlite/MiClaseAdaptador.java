package es.ipo2.notassqlite;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alber on 24/05/2018.
 */

public class MiClaseAdaptador extends ArrayAdapter {

    private Activity context;
    private ArrayList<Nota> notas;
    private ArrayList<Nota> copyNotas = new ArrayList<Nota>();

    public MiClaseAdaptador(Activity context, ArrayList<Nota> notas) {
        super(context, R.layout.adaptador, notas);
        this.context = context;
        this.notas = notas;
        this.copyNotas.addAll(notas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.adaptador, null);

        TextView lblTitulo = v.findViewById(R.id.lblTitulo);
        TextView lblContenido = v.findViewById(R.id.lblContenido);
        TextView lblFecha = v.findViewById(R.id.lblFecha);
        ImageView imagNota = (ImageView)v.findViewById(R.id.imagNota);

        lblTitulo.setText(notas.get(position).getTitulo());
        lblContenido.setText(notas.get(position).getContenido());
        lblFecha.setText(notas.get(position).getFecha());

        switch (notas.get(position).getTipo().toLowerCase())
        {
            case "familiar":
                imagNota.setImageResource(R.mipmap.ic_tipo_familiar);
                break;
            case "deporte":
                imagNota.setImageResource(R.mipmap.ic_tipo_deportes);
                break;
            case "estudios":
                imagNota.setImageResource(R.mipmap.ic_tipo_estudios);
                break;
            case "trabajo":
                imagNota.setImageResource(R.mipmap.ic_tipo_trabajo);
                break;
            case "personal":
                imagNota.setImageResource(R.mipmap.ic_tipo_personal);
                break;
            case "amigos":
                imagNota.setImageResource(R.mipmap.ic_tipo_amigos);
                break;
        }
        return v;
    }

    /* Filtra los datos del adaptador */
    public void filtrar(String texto) {

        // Elimina todos los datos del ArrayList que se cargan en los
        // elementos del adaptador
        notas.clear();
        // Si no hay texto: agrega de nuevo los datos del ArrayList copiado
        // al ArrayList que se carga en los elementos del adaptador
        if (texto.length() == 0) {
            notas.addAll(copyNotas);
        } else {
            // Recorre todos los elementos que contiene el ArrayList copiado
            // y dependiendo de si estos contienen el texto ingresado por el
            // usuario los agrega de nuevo al ArrayList que se carga en los
            // elementos del adaptador.
            for (Nota nota : copyNotas) {

                if (nota.getTitulo().toLowerCase().contains(texto)) {
                    notas.add(nota);
                }
            }
        }
        // Actualiza el adaptador para aplicar los cambios
        notifyDataSetChanged();
    }

}
