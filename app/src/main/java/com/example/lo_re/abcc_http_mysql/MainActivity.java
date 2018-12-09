package com.example.lo_re.abcc_http_mysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void abrirActivities(View view){
        Intent i;
        switch (view.getId()){
            case R.id.btnActivityAgregar: i= new Intent(this, AltasAlumnos.class);
                startActivity(i);
                break;
            case R.id.btnBuscar: i= new Intent(this, ActivityConsultas.class);
                startActivity(i);
                break;

        }
    }
}
