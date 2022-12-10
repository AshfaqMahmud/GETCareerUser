package com.ashfaq.systemproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private static RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Company> list;

    public MyAdapter(Context context, ArrayList<Company> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        MyAdapter.recyclerViewInterface =recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.company_details,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Company company =list.get(position);
        holder.tname.setText(company.getCompanyName());
        holder.tloc.setText(company.getCompanyLoc());
        holder.ttype.setText(company.getCompanyType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tname, tloc, ttype;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.companyname);
            tloc = itemView.findViewById(R.id.companyloc);
            ttype = itemView.findViewById(R.id.companytyp);

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
