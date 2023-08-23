package com.example.proyectocamara;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Button btnCamara;
    ImageView visor;
    CharSequence text;
    int duration;
    Bundle extras;
    Bitmap imgBitmap;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text = "Imagen capturada con exito";
        duration = Toast.LENGTH_SHORT;

        TextView txtUser = (TextView) findViewById(R.id.nomuser);
        Bundle bundle = getIntent().getExtras();
        txtUser.setText(bundle.getString("usuario"));

        btnCamara = findViewById(R.id.btncam);
        //visor = findViewById(R.id.imageV);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });
    }


    //este codigo permite abrir la camara
    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        //permite visualizar la imagen tomada en la siguiente pantalla
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                extras = result.getData().getExtras();
                imgBitmap = (Bitmap) extras.get("data");

                toast = Toast.makeText(MainActivity2.this, text, duration);
                toast.show();//muestra un pequeño mensaje al tomar una foto con exito
            }
        }
    });

    public void siguiente(View view) {
        Intent i = new Intent(this, MainActivity4.class );
        extras.putParcelable("Bitmap", imgBitmap);
        i.putExtras(extras);//envia la imagen bitmap capturada al main activity 4
        startActivity(i);//lleva a la siguiente activity
    }

    public void cerraSesion(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}