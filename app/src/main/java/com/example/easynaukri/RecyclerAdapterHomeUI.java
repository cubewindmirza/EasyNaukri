package com.example.easynaukri;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynaukri.ui.HomeDataList;

import java.util.List;

public class RecyclerAdapterHomeUI extends RecyclerView.Adapter<RecyclerAdapterHomeUI.RecylerHolder> {
    Context context;
    List<HomeJobData> dataList;
    public RecyclerAdapterHomeUI(Context context1,List<HomeJobData> list) {
        this.context=context1;
        this.dataList=list;
    }


    @NonNull
    @Override
    public RecyclerAdapterHomeUI.RecylerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.homeuicarddesign,parent,false);
        return new RecylerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterHomeUI.RecylerHolder holder, int position) {
        final HomeJobData data=dataList.get(position);
        holder.title.setText(data.getTitle());
        holder.imageView.setImageResource(data.getImageId());
        holder.job.setText(data.getJob());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class RecylerHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,job;
        RelativeLayout relativeLayout;
        public RecylerHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.modelhomeui);
            title=itemView.findViewById(R.id.titlehomeui);
            job=itemView.findViewById(R.id.jobhomeui);
            relativeLayout=itemView.findViewById(R.id.relative_recyclehomeui);
        }
    }

}
