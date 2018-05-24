package es.ipo2.notassqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alber on 24/05/2018.
 */

public class MiClaseAdaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Nota> lstNotas;

    public MiClaseAdaptador(Context context, ArrayList<Nota> lstNotas) {
        this.context = context;
        this.lstNotas = lstNotas;
    }

    @Override
    public int getCount() {
        return lstNotas.size();
    }

    @Override
    public Object getItem(int i) {
        return lstNotas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Nota nota = (Nota) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.adaptador,null);
        TextView lblTitulo = view.findViewById(R.id.lblTitulo);
        TextView lblContenido = view.findViewById(R.id.lblContenido);

        lblTitulo.setText(nota.getTitulo());
        lblContenido.setText(nota.getContenido());

        return view;
    }
}
