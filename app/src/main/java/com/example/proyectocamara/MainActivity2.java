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
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            // Crear una instancia de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://localhost:5000") // Reemplaza BASE_URL con la URL de tu servidor Flask
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Crear una instancia de la interfaz ApiService
            ApiService apiService = retrofit.create(ApiService.class);

            // Crear una solicitud JSON
            // Obtener los valores de los campos de entrada
            // Obtener los valores de los campos de entrada desde activity_main2.xml
            EditText edtTextoUser = findViewById(R.id.edtTextoUser);
            EditText edtTextoUser2 = findViewById(R.id.edtTextoUser2);


            // Obtener la ruta de la imagen (debes proporcionar la ruta real aquí)
            String imagePath = "Ruta de tu imagen aquí";

            // Obtener los textos de los campos de entrada
            String promptPositivo = edtTextoUser.getText().toString();
            String promptNegativo = edtTextoUser2.getText().toString();

            // Crear una solicitud JSON con los valores adecuados
            ImageProcessingRequest request = new ImageProcessingRequest(promptPositivo, promptNegativo, imagePath);

            // Enviar la solicitud al servidor para procesar la imagen
            Call<ImageProcessingResponse> call = apiService.generateImage(request);


            // Enviar la solicitud al servidor para procesar la imagen
            call = apiService.generateImage(request);
            call.enqueue(new Callback<ImageProcessingResponse>() {
                @Override
                public void onResponse(Call<ImageProcessingResponse> call, Response<ImageProcessingResponse> response) {
                    if (response.isSuccessful()) {
                        // La respuesta exitosa contiene la ruta de la imagen procesada
                        String outputPath = response.body().getOutputPath();

                        // Obtén una referencia al ImageView
                        ImageView imgDespues = findViewById(R.id.imgDespues);

                        // Cargar y mostrar la imagen procesada con Picasso
                        Picasso.get().load("https://localhost:5000" + "/get_image?output_path=" + outputPath).into(imgDespues);
                    } else {
                        // Manejar errores aquí
                    }
                }


                @Override
                public void onFailure(Call<ImageProcessingResponse> call, Throwable t) {
                    // Manejar errores de red aquí
                }
            });
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