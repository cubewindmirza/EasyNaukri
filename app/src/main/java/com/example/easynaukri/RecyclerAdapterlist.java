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
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynaukri.ui.HomeDataList;
import com.example.easynaukri.ui.homeData;
import com.example.easynaukri.ui.job.job;
import com.example.easynaukri.ui.tools.RecyclerAdapter;

import java.util.List;

public class RecyclerAdapterlist extends RecyclerView.Adapter<RecyclerAdapterlist.RecyclerHolder> {
    Context context;
    List<HomeDataList> dataList;
    public RecyclerAdapterlist(Context context1,List<HomeDataList> list) {
        this.context=context1;
        this.dataList=list;
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cardviewdeisgn_list,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        final HomeDataList data=dataList.get(position);
        holder.title.setText(data.getTitle());
        holder.imageView.setImageResource(data.getImage());
        holder.descp.setText(data.getDesp());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, model.class);
                i.putExtra("title",data.getTitle());
                i.putExtra("image",data.getImage());
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
        TextView title,descp;
        RelativeLayout relativeLayout;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview_recyclelist);
            title=itemView.findViewById(R.id.title_recyclelist);
            descp=itemView.findViewById(R.id.description_recyclelist);
            relativeLayout=itemView.findViewById(R.id.relative_recyclelist);
        }
    }
}
