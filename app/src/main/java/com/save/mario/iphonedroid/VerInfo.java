package com.save.mario.iphonedroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.save.mario.iphonedroid.model.Example;
import com.save.mario.iphonedroid.retrofitUtils.APIRest;
import com.save.mario.iphonedroid.retrofitUtils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerInfo extends AppCompatActivity {

    String id;
    ImageView imageView;
    TextView nombre,descripcion,login,lenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info);

        id= getIntent().getStringExtra("id");
        imageView=findViewById(R.id.image);
        nombre=findViewById(R.id.tvNombreProyecto);
        descripcion=findViewById(R.id.tvDescricionProyecto);
        login=findViewById(R.id.tvNombreUser);
        lenguaje=findViewById(R.id.tvLenguaje);
        invocarWS();

    }

    private void invocarWS() {
        if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRest.BASE_URL);
            APIRest ars = r.create(APIRest.class);

            Call<Example> call = ars.getRepository(id);

            call.enqueue(new Callback<Example>() {

                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {

                    if (!response.isSuccessful()) {
                        Log.e("Error", response.code() + " ");
                    } else {
                        Example e = response.body();
                        nombre.setText(e.getName());
                        descripcion.setText(e.getDescription());
                        login.setText(e.getOwner().getLogin());
                        lenguaje.setText(e.getLanguage());
                        String foto = e.getOwner().getAvatarUrl();
                        Glide.with(imageView.getContext()).load(foto).into(imageView);
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                    Log.e("Error", t.toString());
                }
            });
        }
    }

    private boolean isNetworkAvailable(){
        boolean isAvailable = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()) isAvailable=true;

        return isAvailable;
    }
}
