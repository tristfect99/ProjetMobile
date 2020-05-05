package com.example.applicationandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private List<RestoTrouver> restoTrouverDataSet;
    private OnRestoListener mOnRestoListener;

    MyAdapter(List<RestoTrouver> restoTrouverDataSet, OnRestoListener onRestoListener){
        this.restoTrouverDataSet = restoTrouverDataSet;
        this.mOnRestoListener = onRestoListener;
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewResto;
        public TextView textViewAdresse;
        public TextView textViewNote;

        OnRestoListener onRestoListener;

        public Holder(View itemView, OnRestoListener onRestoListener) {
            super(itemView);
            textViewResto = (TextView) itemView.findViewById(R.id.TextViewNom);
            textViewAdresse = (TextView) itemView.findViewById(R.id.TextViewAdresse);
            textViewNote = (TextView) itemView.findViewById(R.id.TextViewNote);

            this.onRestoListener = onRestoListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRestoListener.onRestoClick(getAdapterPosition());
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell, parent, false);
        return new Holder(view, mOnRestoListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        RestoTrouver restoTrouver = restoTrouverDataSet.get(position);
        holder.textViewResto.setText(restoTrouver.getNom());
        holder.textViewAdresse.setText(restoTrouver.getAdresse());
        String note = "Note: " + restoTrouver.getNote();
        holder.textViewNote.setText(note);
    }

    @Override
    public int getItemCount() {
        return restoTrouverDataSet.size();
    }

    public interface OnRestoListener{
        void onRestoClick(int position);
    }
}
