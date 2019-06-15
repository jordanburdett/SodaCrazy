package com.group4.sodacrazy;

import android.content.Context;
import android.content.Intent;
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
    private List<String> data;
    private Context context;

    public FlavorAdapter(Context context) {
        this.data = new ArrayList<String>();
        this.context = context;
    }

    // again this will be a flavor not a string
    public void setData(List<String> data) {

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

        holder.tvflavor.setText(data.get(position));

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
