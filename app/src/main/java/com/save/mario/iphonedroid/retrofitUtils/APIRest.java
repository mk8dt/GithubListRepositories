package com.save.mario.iphonedroid.retrofitUtils;

import com.save.mario.iphonedroid.model.Example;
import com.save.mario.iphonedroid.model.Owner;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRest {

    String BASE_URL = "https://api.github.com/";

    @GET("repositories")
    Call<ArrayList<Example>> listaProyectos();

    @GET("repositories/{id}")
    Call<Example> getRepository(@Path("id") String id);

    @GET("search/repositories/q{name}")
    Call<ArrayList<Example>> getRepositoryName(@Query("name") String name);


}
