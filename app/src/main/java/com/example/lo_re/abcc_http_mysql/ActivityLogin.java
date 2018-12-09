package com.example.lo_re.abcc_http_mysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import controlador.Analizador_JSON;

public class ActivityLogin extends AppCompatActivity {

    EditText usuario, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txtUsuario);
        password = findViewById(R.id.txtPassword);
    }
    public void login(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    class Login extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            Analizador_JSON analizadorJSON = new Analizador_JSON();
            String url ="http://192.168.1.73/PHP/PHP/Sistema_ABCC_MySQL/web_service_http_android/login.php";
            try {
                JSONObject jsonObject = analizadorJSON.peticionHTTP(url,"POST");
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                for (int i=0;i<jsonArray.length();i++){
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
