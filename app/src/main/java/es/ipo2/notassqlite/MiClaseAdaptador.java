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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alber on 24/05/2018.
 */

public class MiClaseAdaptador extends ArrayAdapter {

    private Activity context;
    private ArrayList<Nota> notas;

    public MiClaseAdaptador(Activity context, ArrayList<Nota> notas) {
        super(context, R.layout.adaptador, notas);
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.adaptador, null);

        TextView lblTitulo = v.findViewById(R.id.lblTitulo);
        TextView lblContenido = v.findViewById(R.id.lblContenido);
        TextView lblFecha = v.findViewById(R.id.lblFecha);

        lblTitulo.setText(notas.get(position).getTitulo());
        lblContenido.setText(notas.get(position).getContenido());
        lblFecha.setText(notas.get(position).getFecha());

        return v;
    }


    /*@Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adaptador,null);
        TextView lblTitulo = view.findViewById(R.id.lblTitulo);
        TextView lblContenido = view.findViewById(R.id.lblContenido);

        lblTitulo.setText(notas.get(i).getTitulo());
        lblContenido.setText(notas.get(i).getContenido());

        return view;
    }*/

}
