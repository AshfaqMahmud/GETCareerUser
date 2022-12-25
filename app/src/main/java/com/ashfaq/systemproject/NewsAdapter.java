package com.ashfaq.systemproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashfaq.systemproject.Models.NewsHeadlines;
import com.ashfaq.systemproject.Models.SelectListeners;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<CustomNewsViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListeners listeners;

    public NewsAdapter(Context context, List<NewsHeadlines> headlines,SelectListeners listeners) {
        this.context = context;
        this.headlines = headlines;
        this.listeners = listeners;
    }

    @NonNull
    @Override
    public CustomNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomNewsViewHolder(LayoutInflater.from(context).inflate(R.layout.header_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomNewsViewHolder holder, int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        //if(headlines.get(position).getUrlToImage()!=null){
            //Picasso.with(context).load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        //}

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeners.onNewsClicked(headlines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
