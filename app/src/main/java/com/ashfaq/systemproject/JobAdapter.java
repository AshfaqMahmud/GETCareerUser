package com.ashfaq.systemproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {
    private static RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Job> list;
    public JobAdapter(Context context, ArrayList<Job> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        MyAdapter.recyclerViewInterface =recyclerViewInterface;
    }
    @NonNull
    @Override
    public JobAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.job_details,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.MyViewHolder holder, int position) {
        Job job =list.get(position);
        holder.jname.setText(job.getJobTitle());
        holder.jtyp.setText(job.getJobSite());
        holder.jcompany.setText(job.getJobCompany());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView jname, jtyp, jcompany;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jname = itemView.findViewById(R.id.job_title);
            jtyp = itemView.findViewById(R.id.job_typ);
            jcompany = itemView.findViewById(R.id.job_company);

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
