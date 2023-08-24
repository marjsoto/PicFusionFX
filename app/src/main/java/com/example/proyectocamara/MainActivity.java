package com.example.proyectocamara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:5000") // Reemplaza BASE_URL con la URL de tu servidor Flask
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia de la interfaz ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Crear una solicitud JSON
        ImageProcessingRequest request = new ImageProcessingRequest();
        request.setPrompt("Tu prompt aquí");
        request.setNegativePrompt("Tu negative prompt aquí");
        request.setImagePath("Ruta de tu imagen aquí");

        // Enviar la solicitud al servidor para procesar la imagen
        Call<ImageProcessingResponse> call = apiService.generateImage(request);
        call.enqueue(new Callback<ImageProcessingResponse>() {
            @Override
            public void onResponse(Call<ImageProcessingResponse> call, Response<ImageProcessingResponse> response) {
                if (response.isSuccessful()) {
                    // La respuesta exitosa contiene la ruta de la imagen procesada
                    String outputPath = response.body().getOutputPath();

                    // Cargar y mostrar la imagen procesada con Picasso
                    Picasso.get().load("https://localhost:5000" + "/get_image?output_path=" + outputPath).into(MainActivity4.class.imgDespues);
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

    public void inicio(View view) {
        EditText etUser = (EditText) findViewById(R.id.edtUsuario);
        Intent i = new Intent(this, MainActivity2.class );
        i.putExtra("usuario", etUser.getText().toString()); //obtiene el usuario para el mensaje de bienvenida
        startActivity(i);//
    }

    public void registro(View view) {
        EditText etUser = (EditText) findViewById(R.id.edtUsuario);
        Intent i = new Intent(this, MainActivity3.class );
        i.putExtra("usuario", etUser.getText().toString()); //obtiene el usuario para el registro
        startActivity(i);//abre la clase registro
    }
}