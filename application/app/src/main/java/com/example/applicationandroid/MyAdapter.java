package com.example.applicationandroid;

import android.app.AlertDialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    private HashMap<String, String> mItems;

    MyAdapter(HashMap<String, String> items) {
        mItems = items;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public TextView textViewResto;
        public TextView textViewAdresse;

        public Holder(View itemView) {
            super(itemView);
            textViewResto = (TextView) itemView.findViewById(R.id.TextViewNom);
            textViewAdresse = (TextView) itemView.findViewById(R.id.TextViewAdresse);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String nom = (String) mItems.keySet().toArray()[position];
        holder.textViewResto.setText(nom);

        /*Iterator myVeryOwnIterator = mItems.keySet().iterator();
        String nom = (String)myVeryOwnIterator.next();
        String adresse = (String)mItems.get(nom);
        holder.textViewResto.setText(nom);
        holder.textViewAdresse.setText(adresse);*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
