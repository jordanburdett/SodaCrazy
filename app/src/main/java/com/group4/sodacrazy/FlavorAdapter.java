package com.group4.sodacrazy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the adapter that controls the flavor recyclerView on the main screen.
 */
public class FlavorAdapter extends RecyclerView.Adapter<FlavorAdapter.ViewHolder> {

    // Currently just holds a list of strings..
    // this will eventually be a list of flavors
    private List<FlavorItem> data;
    private Context context;

    /**
     * Non-default constructor
     * Needs to know the context when created.
     *
     * @param context Where we are coming from.
     */
    public FlavorAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    /**
     * Sets the data for a single Text view in the
     *
     * @param data ArrayList of flavorItems
     */
    public void setData(List<FlavorItem> data) {

        this.data = data;
    }

    /**
     * This sets up the view so It is ready to be used.
     *
     * @return holder
     */
    @Override
    public FlavorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.flavor_list_layout, parent, false);

        return new ViewHolder(view);
    }

    /**
     * This controls what happens for each textView for each element in the array.
     *
     * @param holder   Where the textView is stored.
     * @param position Where in the array?
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvFlavor.setText(data.get(position).name);
        holder.tvFlavor.setTextColor(Color.parseColor(data.get(position).color));
        holder.tvFlavor.setShadowLayer(2.5f, -2, 2, Color.BLACK);

    }

    /**
     * Gets the number of items in RecyclerView
     *
     * @return size
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ViewHolder class
     *
     * This is used by the FlavorAdapter to control the text views
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFlavor;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFlavor = itemView.findViewById(R.id.tv_flavor);
        }
    }
}
