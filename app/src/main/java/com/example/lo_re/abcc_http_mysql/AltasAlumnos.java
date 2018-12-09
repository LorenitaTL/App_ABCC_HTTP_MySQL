package com.example.lo_re.abcc_http_mysql;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import controlador.Analizador_JSON;

public class AltasAlumnos extends AppCompatActivity {
    EditText caja_nc, caja_nom, caja_pap, caja_sap, caja_edad, caja_ca, caja_sem;
    private static final String TAG = AltasAlumnos.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas_alumnos);
        caja_nc = findViewById(R.id.caja_num_control);
        caja_nom = findViewById(R.id.caja_nombre);
        caja_pap = findViewById(R.id.caja_pap);
        caja_sap = findViewById(R.id.caja_sap);
        caja_edad = findViewById(R.id.caja_edad);
        caja_ca = findViewById(R.id.caja_carrera);
        caja_sem =  findViewById(R.id.caja_semestre);
    }

    public void agregarAlumno(View view){
        Log.i(TAG, "AGREGAR");
         String nc = caja_nc.getText().toString();
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
            new AgregarAlumno().execute(nc,n,pa,sa,e,s,c);
            Toast toast1 = Toast.makeText(this, "Registro agregado correctamente", Toast.LENGTH_SHORT);
        }

    }//Método agregar alumno

    //Clase interna para realizar el envío de datos en otro Hilo de ejecución

    class AgregarAlumno extends AsyncTask<String, String,String>{

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
            String url ="http://192.168.1.73/PHP/PHP/Sistema_ABCC_MySQL/web_service_http_android/altas_alumnos.php";
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
