package com.example.lo_re.abcc_http_mysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {

    EditText usuario, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txtUsuario);
        password = findViewById(R.id.txtPassword);
    }
}
