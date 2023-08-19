package com.example.proyectocamara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inicio(View view) {
        EditText etUser = (EditText) findViewById(R.id.edtUsuario);
        Intent i = new Intent(this, MainActivity2.class );
        i.putExtra("usuario", etUser.getText().toString()); //obtiene el usuario para el mensaje de bienvenida
        startActivity(i);
    }

    public void registro(View view) {
        EditText etUser = (EditText) findViewById(R.id.edtUsuario);
        Intent i = new Intent(this, MainActivity3.class );
        i.putExtra("usuario", etUser.getText().toString()); //obtiene el usuario para el registro
        startActivity(i);//abre la clase registro
    }
}