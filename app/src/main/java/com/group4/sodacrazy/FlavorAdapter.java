package com.group4.sodacrazy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FlavorAdapter extends RecyclerView.Adapter<FlavorAdapter.ViewHolder> {

    // Currently just holds a list of strings..
    // this will eventually be a list of flavors
    private List<FlavorItem> data;
    private Context context;

    public FlavorAdapter(Context context) {
        this.data = new ArrayList<FlavorItem>();
        this.context = context;
    }

    // again this will be a flavor not a string
    public void setData(List<FlavorItem> data) {

        this.data = data;
    }

    @Override
    public FlavorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.flavor_list_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvflavor.setText(data.get(position).name);
        holder.tvflavor.setTextColor(Color.parseColor(data.get(position).color));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvflavor;

        public ViewHolder(View itemView) {
            super(itemView);
            tvflavor = itemView.findViewById(R.id.tv_flavor);
        }
    }
}
