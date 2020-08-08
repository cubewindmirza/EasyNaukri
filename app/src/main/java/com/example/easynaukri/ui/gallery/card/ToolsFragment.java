package com.example.easynaukri.ui.gallery.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynaukri.R;
import com.example.easynaukri.RecyclerAdapterlist;
import com.example.easynaukri.color_management;
import com.example.easynaukri.ui.HomeDataList;
import com.example.easynaukri.ui.tools.RecyclerAdapter;
import com.example.easynaukri.ui.tools.ToolsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference storageReference1;
    RecyclerView recyclerView;
    RecyclerAdapterlist adapter;
    ArrayList<HomeDataList> list;
    GridLayout gridLayout;
    RelativeLayout relativeLayout;
    color_management color_theme;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        //checkpdf();
        View root = inflater.inflate(R.layout.nav_card_fragment, container, false);
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Home");
        gridLayout=root.findViewById(R.id.grid_background_card);
        relativeLayout=root.findViewById(R.id.relative_recycle);
        recyclerView=root.findViewById(R.id.recyclerview);
        color_theme=new color_management(getActivity());
        //relativeLayout.setBackgroundResource(R.drawable.brown_grid_back);
        //checkThemeColor();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<HomeDataList>();
        list.add(new HomeDataList("WHY US",R.drawable.aboutus,"Wondering Why us? Because we provide the best suitable job that is comfortable for u.We Just take  few of your information to make sure which job is best for you."));
        list.add(new HomeDataList("WHY REGISTRATION FEE?",R.drawable.payment,"Why We Charge? 1.For Maintenance 2.Platform Fee"));
        list.add(new HomeDataList("WANT TO KNOW OUR LOCATION?",R.drawable.address,"We provide job all over the Following cities 1.Bangalore 2.Bihar"));
        list.add(new HomeDataList("CONTACT US",R.drawable.number,"You can contact us on below Information easynaukri0@gmail.com easynaukri01@gmail.com"));
        adapter= new RecyclerAdapterlist(getActivity(),list);
        recyclerView.setAdapter(adapter);
        return root;
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
            gridLayout.setBackgroundResource(R.drawable.blue_grid_back);
        }
        else if(color.equals(red)){
            gridLayout.setBackgroundResource(R.drawable.red_grid_back);
        }
        else if(color.equals(green)){

            gridLayout.setBackgroundResource(R.drawable.green_grid_back);
        }
        else if(color.equals(orange)){

            gridLayout.setBackgroundResource(R.drawable.orange_grid_back);
        }
        else if(color.equals(yellow)){

            gridLayout.setBackgroundResource(R.drawable.yellow_grid_back);
        }
        else if(color.equals(dark)){

            gridLayout.setBackgroundResource(R.drawable.dark_grid_back);
        }
        else if(color.equals(brown)){

            gridLayout.setBackgroundResource(R.drawable.brown_grid_back);
        }
    }

}