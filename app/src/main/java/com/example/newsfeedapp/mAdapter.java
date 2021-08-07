package com.example.newsfeedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {

    static ItemClicked activity;
    private final List<News> news;

    public mAdapter(List<News> list, Context context) {
        news = list;
        activity = (ItemClicked) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(news.get(position));

        holder.titleTxtV.setText(news.get(position).getTitle());
        holder.typeTxtV.setText(news.get(position).getType());
        holder.authorTxtV.setText(news.get(position).getAuthor());
        holder.dateTxtV.setText(news.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxtV;
        TextView typeTxtV;
        TextView authorTxtV;
        TextView dateTxtV;

        public ViewHolder(final View itemView) {
            super(itemView);
            titleTxtV = itemView.findViewById(R.id.titleTextView);
            typeTxtV = itemView.findViewById(R.id.typeTextView);
            authorTxtV = itemView.findViewById(R.id.authorTextView);
            dateTxtV = itemView.findViewById(R.id.dateTextView);
            itemView.setOnClickListener(view -> activity.onItemClicked(news.indexOf(itemView.getTag())));
        }
    }
}