package com.save.mario.iphonedroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.save.mario.iphonedroid.adapter.Adapter;
import com.save.mario.iphonedroid.model.Example;
import com.save.mario.iphonedroid.model.ItemOwner;
import com.save.mario.iphonedroid.retrofitUtils.APIRest;
import com.save.mario.iphonedroid.retrofitUtils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private LinearLayoutManager llm;
    private RecyclerView recyclerView;
    private ArrayList<ItemOwner> lista;
    private ArrayList<Example> listaEx;
    private Adapter adapter;
    private ProgressBar pb;
    private String busqueda=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista= new ArrayList<ItemOwner>();
        listaEx= new ArrayList<Example>();
        pb= findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        invocarWS();

    }

    private void invocarWS() {
        if(isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRest.BASE_URL);
            APIRest ars = r.create(APIRest.class);

            Call<ArrayList<Example>> call = ars.listaProyectos();

            call.enqueue(new Callback<ArrayList<Example>>() {

                @Override
                public void onResponse(Call<ArrayList<Example>> call, Response<ArrayList<Example>>response) {

                    if (!response.isSuccessful()) {
                        Log.e("Error", response.code() + " ");
                    } else {
                        pb.setVisibility(View.GONE);
                        listaEx = response.body();
                        ItemOwner itemOwner = null;
                        Log.e("lista",listaEx.get(0).getOwner().toString());
                        for (int i =0 ; i<listaEx.size();i++) {
                            itemOwner = new ItemOwner(listaEx.get(i).getId().toString()
                                    ,listaEx.get(i).getOwner().getLogin()
                                    ,listaEx.get(i).getDescription());
                            lista.add(itemOwner);
                        }
                        configurarRV();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Example>> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        }
    }

    private void configurarRV() {
        recyclerView= findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        llm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter=new Adapter(lista);
        addOnclik();
        recyclerView.setAdapter(adapter);
    }

    private void addOnclik() {
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.indexOfChild(v);
                Example ex = listaEx.get(recyclerView.getChildAdapterPosition(v));
                Intent i = new Intent(getApplicationContext(),VerInfo.class);
                //i.putExtra("Item",ex.getOwner().toString());
                i.putExtra("id",ex.getId().toString());
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.buscar);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pb.setVisibility(View.VISIBLE);
                lista.clear();
                busqueda=searchView.getQuery().toString();
                busqueda(busqueda);
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                busqueda = newText;
                return true;
            }
        });
        return true;
    }

    private void busqueda(String busqueda){

        if(isNetworkAvailable()) {
            Retrofit retrofit = RetrofitClient.getClient(APIRest.BASE_URL);
            APIRest apiRest = retrofit.create(APIRest.class);

            Call<ArrayList<Example>> call = apiRest.getRepositoryName(busqueda);

            call.enqueue(new Callback<ArrayList<Example>>() {

                @Override
                public void onResponse(Call<ArrayList<Example>> call, Response<ArrayList<Example>>response) {

                    if (!response.isSuccessful()) {
                        Log.e("Error", response.code() + " ");
                    } else {
                        pb.setVisibility(View.GONE);
                        listaEx = response.body();
                        ItemOwner itemOwner = null;
                        Log.e("lista",listaEx.get(0).getOwner().toString());
                        for (int i =0 ; i<listaEx.size();i++) {
                            itemOwner = new ItemOwner(listaEx.get(i).getId().toString()
                                    ,listaEx.get(i).getOwner().getLogin()
                                    ,listaEx.get(i).getDescription());
                            lista.add(itemOwner);
                        }
                        configurarRV();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Example>> call, Throwable t) {

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
