package com.example.lo_re.abcc_http_mysql;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controlador.Analizador_JSON;

public class ActivityConsultas extends AppCompatActivity  {
    ListView listViewAlumnos;
    private Adaptador adaptador;
    private Datos dat;
    String nc;
    //ArrayAdapter <String> adapter;
    //ArrayList <String> arrayList = new ArrayList<>();
    ArrayList <Datos> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        listViewAlumnos = findViewById(R.id.listview);
        new MostrarAlumnos().execute();
        adaptador = new Adaptador(this, arrayList);
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listViewAlumnos.setAdapter(adaptador);
        listViewAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Datos d = arrayList.get(position);
                nc = d.getNc();
                Log.i("Dato lv", d.getNc());
                dialog(nc, d);

            }
        });
    }
    public void dialog(final String nc, final Datos datos){

        new AlertDialog.Builder(ActivityConsultas.this)
                .setTitle("Three Buttons")
                .setMessage("Where do you want to go?")
                .setPositiveButton("MODIFICAR DATOS",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                modificar(datos);
                                dialog.cancel();
                            }
                        })
                .setNeutralButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("","You want to go to the CENTER.");
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("ELIMINAR REGISTRO",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Mensaje eliminar", "Eliminar alumno");

                                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //Verificar conectividad wifi
                                NetworkInfo ni = cm.getActiveNetworkInfo();

                                if (ni!=null && ni.isConnected()){
                                    //conectarse, enviar datos para guardar  en MySQL
                                    new EliminarAlumnos().execute(nc);
                                }
                                Log.i("","You want to go to the LEFT.");
                                dialog.cancel();
                                Toast toast1 = Toast.makeText(getApplicationContext(), "Registro eliminado correctamente", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                        }).show();

    }
    public void modificar(Datos datos){
        Intent i= new Intent(this, ActivityCambios.class);
        i.putExtra("nc",datos.getNc());
        i.putExtra("n",datos.getN());
        i.putExtra("pa",datos.getPa());
        i.putExtra("sa",datos.getSa());
        i.putExtra("e",datos.getE());
        i.putExtra("s",datos.getS());
        i.putExtra("c",datos.getC());
        startActivity(i);
    }


    class MostrarAlumnos extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            Analizador_JSON analizadorJSON = new Analizador_JSON();
            String url ="http://192.168.1.73/PHP/PHP/Sistema_ABCC_MySQL/web_service_http_android/consulta.php";
            try {
                JSONObject jsonObject = analizadorJSON.peticionHTTP(url,"POST");
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                for (int i=0;i<jsonArray.length();i++){
                    arrayList.add(new Datos(jsonArray.getJSONObject(i).getString("nc"),
                            jsonArray.getJSONObject(i).getString("n")+" ",
                            jsonArray.getJSONObject(i).getString("pa")+" ",
                            jsonArray.getJSONObject(i).getString("sa"),
                            jsonArray.getJSONObject(i).getString("e"),
                            jsonArray.getJSONObject(i).getString("s"),
                            jsonArray.getJSONObject(i).getString("c")));
                    Log.i("Info >>>>", String.valueOf(arrayList.get(i)));
                    }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class EliminarAlumnos extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... datos) {
            Map<String, String> mapDatos = new HashMap<String, String>();
            mapDatos.put("nc",datos[0]);

            Analizador_JSON analizadorJSON = new Analizador_JSON();
            //GENYMOTION localhost 10.0.3.2
            String url ="http://192.168.1.73/PHP/PHP/Sistema_ABCC_MySQL/web_service_http_android/bajas_alumnos.php";
            String metodo = "POST";

            JSONObject resultado = null;
            try {
                resultado = analizadorJSON.peticionHTTP(url,metodo, mapDatos);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int r =0;
            try {
                r = resultado.getInt("exito");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (r==1){
                Log.i("Msj resultado","REGISTRO AGREGADO");
            }else {
                Log.i("Msj resultado","ERROR");
            }

            return null;
        }
    }
}
