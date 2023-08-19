package com.example.proyectocamara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView txtUser = (TextView) findViewById(R.id.nomuser);
        Bundle bundle = getIntent().getExtras();
        txtUser.setText(bundle.getString("usuario"));

        Button btnCamara = (Button) findViewById(R.id.btncam);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public void cerraSesion(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}