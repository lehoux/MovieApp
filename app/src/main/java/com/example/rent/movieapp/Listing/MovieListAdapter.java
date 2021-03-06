package com.example.rent.movieapp.listing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.movieapp.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by RENT on 2017-03-08.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private List<MovieItem> items = Collections.emptyList();

    public MovieListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MovieListAdapter.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieItem movieItem = items.get(position);
        Glide.with(holder.poster.getContext()).load(items.get(position).getPoster()).into(holder.poster);
        holder.titleAndYear.setText(movieItem.getTitle() + " (" + movieItem.getYear() + ")");
        holder.type.setText("typ: " + movieItem.getType());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setItems(List<MovieItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<MovieItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
}