package com.save.mario.iphonedroid.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.save.mario.iphonedroid.R;
import com.save.mario.iphonedroid.model.ItemOwner;
import com.save.mario.iphonedroid.model.Owner;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.VhGit> implements View.OnClickListener {

    private ArrayList<ItemOwner> listaGit;
    private View.OnClickListener listener;

    public Adapter (ArrayList<ItemOwner> lista){
        this.listaGit=lista;
    }


    @Override
    public Adapter.VhGit onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista,parent,false);
        v.setOnClickListener(this);
        VhGit vh = new VhGit(v);

        return vh;
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(Adapter.VhGit holder, int position) {

        holder.tvID.setText(listaGit.get(position).getId());
        holder.tvNombre.setText(listaGit.get(position).getNombre());
        holder.tvDescripcion.setText(listaGit.get(position).getDescipcion());
    }

    @Override
    public int getItemCount() {
        if(listaGit!=null){
            return listaGit.size();
        }else{
            return 0;
        }

    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class VhGit extends RecyclerView.ViewHolder{

        TextView tvID;
        TextView tvNombre;
        TextView tvDescripcion;

        public VhGit(View itemView) {
            super(itemView);
            tvID=itemView.findViewById(R.id.tvId);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvDescripcion=itemView.findViewById(R.id.tvDescricion);
        }

        public TextView getTvID() {
            return tvID;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }


        public TextView getTvDescripcion() {
            return tvDescripcion;
        }

    }
}
