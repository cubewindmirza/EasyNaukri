package com.example.easynaukri.ui.tools;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynaukri.ApplyJob;
import com.example.easynaukri.R;
import com.example.easynaukri.model;
import com.example.easynaukri.ui.homeData;
import android.app.FragmentManager;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder > {
    Context context;
    List<homeData> dataList;
    public RecyclerAdapter(Context context1,List<homeData> list) {
        this.context=context1;
        this.dataList=list;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cardviewdesign,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
    final homeData data=dataList.get(position);
    holder.title.setText(data.getTitle());
    holder.imageView.setImageResource(data.getImage());
    holder.descp.setText(data.getDescprition());
    holder.descp1.setText(data.getDescprition1());
    holder.descp2.setText(data.getDescprition2());
    holder.descp3.setText(data.getDescprition3());
    holder.descp4.setText(data.getDescprition4());
    holder.descp5.setText(data.getDescprition5());
    holder.salary.setText(data.getAmount());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(context, ApplyJob.class);
                i.putExtra("title",data.getTitle());
                i.putExtra("image",data.getImage());
                i.putExtra("salary",data.getAmount());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,descp,descp1,descp2,descp3,descp4,descp5,salary;
        RelativeLayout relativeLayout;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview_recycle);
            title=itemView.findViewById(R.id.title_recycle);
            descp=itemView.findViewById(R.id.description_recycle);
            descp1=itemView.findViewById(R.id.descp1);
            descp2=itemView.findViewById(R.id.descp2);
            descp3=itemView.findViewById(R.id.descp3);
            descp4=itemView.findViewById(R.id.descp4);
            descp5=itemView.findViewById(R.id.descp5);
            salary=itemView.findViewById(R.id.salary_model);
            relativeLayout=itemView.findViewById(R.id.relative_recycle);
        }
    }
}
