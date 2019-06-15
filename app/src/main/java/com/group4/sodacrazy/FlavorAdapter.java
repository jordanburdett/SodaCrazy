package com.group4.sodacrazy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
                R.layout.
        )
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FlavorAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
