package com.example.easynaukri.ui.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.easynaukri.JobData;
import com.example.easynaukri.R;
import com.example.easynaukri.RecyclerAdapterlist;
import com.example.easynaukri.color_management;
import com.example.easynaukri.ui.HomeDataList;
import com.example.easynaukri.ui.homeData;
import com.example.easynaukri.ui.tools.RecyclerAdapter;

import java.util.ArrayList;

public class job extends Fragment {

    private JobViewModel mViewModel;

    public static job newInstance() {
        return new job();
    }
    RecyclerView recyclerView;
    RecyclerAdapterlist adapter;
    ArrayList<HomeDataList> list;
    GridLayout gridLayout;
    RelativeLayout relativeLayout;
    color_management color_theme;
    LinearLayout layout;
    public String passcolor="";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.job_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JobViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         recyclerView=view.findViewById(R.id.job_recycler);
        color_theme=new color_management(getActivity());
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Jobs");
        gridLayout=view.findViewById(R.id.grid_background_card);
        relativeLayout=view.findViewById(R.id.relative_recyclelist);
        layout=view.findViewById(R.id.linear_jobback1);
       // recyclerView=view.findViewById(R.id.recyclerview);
        //relativeLayout.setBackgroundResource(R.drawable.brown_grid_back);
        checkThemeColor();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<HomeDataList>();
        /*JobData jobData=new JobData();//to get data class
        list.add(new homeData(jobData.webtitle,R.drawable.webdevleopment,jobData.webdeveloper,jobData.webskill1,jobData.webskill2));
        list.add(new homeData(jobData.apptitle,R.drawable.appdevelopment,jobData.appdeveloper, jobData.appskill1,""));
        list.add(new homeData(jobData.graphictitle,R.drawable.graphicdesign,jobData.graphicdesign,jobData.graphicskill1, jobData.getGraphicskill2));
        list.add(new homeData(jobData.uiuxtitle,R.drawable.uiux,jobData.uiux,jobData.uiuxskill1,jobData.getUiuxskill2));
        list.add(new homeData(jobData.softwaretitle,R.drawable.softwaredeveloper,jobData.softwaredeveloper,jobData.softwareskill1,jobData.softwareskill2));
        list.add(new homeData(jobData.databasetitle,R.drawable.databaseadministrator,jobData.databaseadmin, jobData.databaseskill1,jobData.databaseskill2));
        list.add(new homeData(jobData.devopstitle,R.drawable.devops,jobData.devops,jobData.devopsskill1, jobData.devopsskill2));
        list.add(new homeData(jobData.gamedevelopertitle,R.drawable.gamedeveloper,jobData.gamedeveloper,jobData.gamedevleoperskill1,jobData.gamedevleoperskill2));

         */
        list.add(new HomeDataList("SOFTWARE FIELD",R.drawable.softwaredeveloper,"Click"));
        list.add(new HomeDataList("SALES AND MARKETING",R.drawable.salesandmarketing,"Click"));
        list.add(new HomeDataList("ACCOUNT AND FINANCE",R.drawable.accountsandfinance,"Click"));
        adapter=new RecyclerAdapterlist(getActivity(),list);
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);

    }
    public void checkThemeColor(){
        String blue="blue";
        String red="red";
        String green="green";
        String orange="orange";
        String yellow="yellow";
        String dark="dark";
        String brown="brown";
        String color=color_theme.getColor();
        if(color.equals(blue)){
            //linearLayout1.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout2.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout3.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout4.setBackgroundResource(R.drawable.gradient_navheader);
            passcolor="blue";
           layout.setBackgroundResource(R.drawable.blue_grid_back);
        }
        else if(color.equals(red)){
            layout.setBackgroundResource(R.drawable.red_grid_back);
            passcolor="red";
        }
        else if(color.equals(green)){
            layout.setBackgroundResource(R.drawable.green_grid_back);
            passcolor="green";
        }
        else if(color.equals(orange)){

            layout.setBackgroundResource(R.drawable.orange_grid_back);
            passcolor="orange";
        }
        else if(color.equals(yellow)){

            layout.setBackgroundResource(R.drawable.yellow_grid_back);
            passcolor="yellow";
        }
        else if(color.equals(dark)){

            layout.setBackgroundResource(R.drawable.dark_grid_back);
            passcolor="dark";
        }
        else if(color.equals(brown)){

            layout.setBackgroundResource(R.drawable.brown_grid_back);
            passcolor="brown";
        }
    }

}