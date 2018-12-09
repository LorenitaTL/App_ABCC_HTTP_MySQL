package com.example.lo_re.abcc_http_mysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList <Datos> listitem;


    public Adaptador(Context context, ArrayList<Datos> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Datos Item = (Datos) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        TextView tvNumControl = convertView.findViewById(R.id.tvNumControl);
        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        TextView tvPrimerAp = convertView.findViewById(R.id.tvPrimerAp);
        TextView tvSegundoAp = convertView.findViewById(R.id.tvSegundoAp);
        TextView tvEdad = convertView.findViewById(R.id.tvEdad);
        TextView tvSemestre = convertView.findViewById(R.id.tvSemestre);
        TextView tvCarrera = convertView.findViewById(R.id.tvCarrera);

        tvNumControl.setText(Item.getNc());
        tvNombre.setText(Item.getN());
        tvPrimerAp.setText(Item.getPa());
        tvSegundoAp.setText(Item.getSa());
        tvEdad.setText(Item.getE());
        tvSemestre.setText(Item.getS());
        tvCarrera.setText(Item.getC());
        return convertView;
    }
}
