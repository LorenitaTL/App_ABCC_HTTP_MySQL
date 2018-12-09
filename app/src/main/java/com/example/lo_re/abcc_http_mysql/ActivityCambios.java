package com.example.lo_re.abcc_http_mysql;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import controlador.Analizador_JSON;

public class ActivityCambios extends AppCompatActivity {
    EditText caja_nom, caja_pap, caja_sap, caja_edad, caja_ca, caja_sem;
    TextView tv_nc;
    Datos d;
    String nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);
        tv_nc = findViewById(R.id.tv_nc);
        caja_nom = findViewById(R.id.caja_nombre);
        caja_pap = findViewById(R.id.caja_pap);
        caja_sap = findViewById(R.id.caja_sap);
        caja_edad = findViewById(R.id.caja_edad);
        caja_ca = findViewById(R.id.caja_carrera);
        caja_sem =  findViewById(R.id.caja_semestre);

        tv_nc.setText(getIntent().getStringExtra("nc"));
        caja_nom.setText(getIntent().getStringExtra("n"));
        caja_pap.setText(getIntent().getStringExtra("pa"));
        caja_sap.setText(getIntent().getStringExtra("sa"));
        caja_edad.setText(getIntent().getStringExtra("e"));
        caja_sem.setText(getIntent().getStringExtra("s"));
        caja_ca.setText(getIntent().getStringExtra("c"));
    }

    public void modificarAlumno(View view){
        Log.i("", "MODIFICAR");
        String nc = tv_nc.getText().toString();
        String n = caja_nom.getText().toString();
        String pa = caja_pap.getText().toString();
        String sa = caja_sap.getText().toString();
        String e = caja_edad.getText().toString();
        String s = caja_sem.getText().toString();
        String c = caja_ca.getText().toString();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //Verificar conectividad wifi
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni!=null && ni.isConnected()){
            //conectarse, enviar datos para guardar  en MySQL
            new ModificarrAlumno().execute(nc,n,pa,sa,e,s,c);
        }


    }//Método modificar alumno

    //Clase interna para realizar el envío de datos en otro Hilo de ejecución

    class ModificarrAlumno extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... datos) {
            Map<String, String> mapDatos = new HashMap<String, String>();
            mapDatos.put("nc",datos[0]);
            mapDatos.put("n",datos[1]);
            mapDatos.put("pa",datos[2]);
            mapDatos.put("sa",datos[3]);
            mapDatos.put("e",datos[4]);
            mapDatos.put("s",datos[5]);
            mapDatos.put("c",datos[6]);

            Analizador_JSON analizadorJSON = new Analizador_JSON();
            //GENYMOTION localhost 10.0.3.2
            String url ="http://192.168.1.73/PHP/PHP/Sistema_ABCC_MySQL/web_service_http_android/cambios_alumnos.php";
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
                Log.i("Msj resultado","REGISTRO MODIFICADO");

            }else {
                Log.i("Msj resultado","ERROR");
            }
            return null;
        }
    }

}
