package com.ashfaq.systemproject;

import static com.ashfaq.systemproject.MyAdapter.recyclerViewInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    static RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Review> list;
    public ReviewAdapter(Context context, ArrayList<Review> list, RecyclerViewInterface recyclerViewInterface){
        this.context= context;
        this.list=list;
        ReviewAdapter.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.review_part,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        Review review = list.get(position);
        holder.rno.setText(review.getRev_no());
        holder.rstar.setText(review.getRev_star());
        holder.rfed.setText(review.getRev_fed());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView rno, rstar, rfed;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rno = itemView.findViewById(R.id.review_no);
            rstar = itemView.findViewById(R.id.review_star);
            rfed = itemView.findViewById(R.id.review_fed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null)
                    {
                        int pos =getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
