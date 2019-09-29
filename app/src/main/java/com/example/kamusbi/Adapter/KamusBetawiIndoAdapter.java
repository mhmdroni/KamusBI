package com.example.kamusbi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.kamusbi.Model.Kamus;
import com.example.kamusbi.R;

public class KamusBetawiIndoAdapter extends RecyclerView.Adapter<KamusBetawiIndoAdapter.MyViewHolder> {
    List<Kamus> kamusList;
    Context context;


    public KamusBetawiIndoAdapter(List<Kamus> KamusList, Context context) {
        this.kamusList = kamusList;
        this.context = context;
    }

    @NonNull
    @Override
    public KamusBetawiIndoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kamus_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusBetawiIndoAdapter.MyViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(position + 1));
        holder.tvBasanya.setText(kamusList.get(position).getIndo());
        holder.tvIndo.setText(kamusList.get(position).getBetawi());
    }

    @Override
    public int getItemCount() {
        return kamusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvBasanya;
        TextView tvIndo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvBasanya = (TextView) itemView.findViewById(R.id.tvBasanya);
            tvIndo = (TextView) itemView.findViewById(R.id.tvIndo);
        }
    }
}
