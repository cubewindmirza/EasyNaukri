package com.example.easynaukri.ui.homeui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynaukri.HomeJobData;
import com.example.easynaukri.R;
import com.example.easynaukri.RecyclerAdapterHomeUI;
import com.example.easynaukri.RecyclerAdapterlist;
import com.example.easynaukri.color_management;
import com.example.easynaukri.ui.HomeDataList;

import java.util.ArrayList;

public class homeui extends Fragment {
    RecyclerView recyclerView;
    LinearLayout layout;
    color_management color_theme;
    RecyclerAdapterHomeUI adapter;
    ArrayList<HomeJobData> list;
    private HomeuiViewModel mViewModel;

    public static homeui newInstance() {
        return new homeui();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homeui_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeuiViewModel.class);
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Home");
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_homeui);
        layout=view.findViewById(R.id.linear_backhomeui);
        color_theme=new color_management(getContext());
        checkThemeColor();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        /*RecyclerView.SmoothScroller smoothScrollers=new LinearSmoothScroller(getContext()){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

         */
     //   smoothScrollers.setTargetPosition(120);
        //linearLayoutManager.startSmoothScroll(smoothScrollers);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<HomeJobData>();
                list.add(new HomeJobData("Rahul Singh",R.drawable.rahulsingh,"Software Engineer"));
                list.add(new HomeJobData("Binsa",R.drawable.binsa,"Sales Executive"));
                list.add(new HomeJobData("Varun",R.drawable.varun,"Cyber Security Expert"));
                list.add(new HomeJobData("Anusha",R.drawable.anusha,"Backend Developer"));
                list.add(new HomeJobData("Preethi",R.drawable.preethi,"Assistant Sales Manager"));
                list.add(new HomeJobData("Melvin",R.drawable.melvin,"Graphic Designer"));
                list.add(new HomeJobData("Chaaru",R.drawable.chaaru,"Web Developer"));
                list.add(new HomeJobData("Anjali",R.drawable.anjali,"Software Testing"));
                list.add(new HomeJobData("Bharath",R.drawable.bharath,"UIUX Designer "));
                list.add(new HomeJobData("Sharath",R.drawable.sharath,"Database Administartor "));
                list.add(new HomeJobData("Karan",R.drawable.karan,"Game Developer "));
                list.add(new HomeJobData("Bhoomika",R.drawable.bhoomika,"Digital Marketing "));
                list.add(new HomeJobData("Nisha",R.drawable.nisha,"Sales Manager "));
                list.add(new HomeJobData("Pooja",R.drawable.pooja,"Accountant "));
                list.add(new HomeJobData("Akshay",R.drawable.akshay,"Finance Manager "));

        adapter=new RecyclerAdapterHomeUI(getContext(),list);
        recyclerView.setAdapter(adapter);



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
            layout.setBackgroundResource(R.drawable.blue_grid_back);
        }
        else if(color.equals(red)){
            layout.setBackgroundResource(R.drawable.red_grid_back);
        }
        else if(color.equals(green)){
            layout.setBackgroundResource(R.drawable.green_grid_back);

        }
        else if(color.equals(orange)){

            layout.setBackgroundResource(R.drawable.orange_grid_back);

        }
        else if(color.equals(yellow)){

            layout.setBackgroundResource(R.drawable.yellow_grid_back);

        }
        else if(color.equals(dark)){

            layout.setBackgroundResource(R.drawable.dark_grid_back);

        }
        else if(color.equals(brown)){

            layout.setBackgroundResource(R.drawable.brown_grid_back);

        }
    }

}