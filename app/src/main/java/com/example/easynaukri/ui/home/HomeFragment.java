package com.example.easynaukri.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easynaukri.R;
import com.example.easynaukri.UserProfileData;
import com.example.easynaukri.color_management;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    CircleImageView circleImageView1,circleImageView2,circleImageView3,circleImageView4;
    TextView textView1,textView2,textView3,textView4;
    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    color_management color_theme;
    GridLayout gridLayout;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridLayout=view.findViewById(R.id.grid_background);
        circleImageView1=view.findViewById(R.id.home_profile1);
        circleImageView2=view.findViewById(R.id.home_profile2);
        circleImageView3=view.findViewById(R.id.home3);
        circleImageView4=view.findViewById(R.id.home4);
        textView1=view.findViewById(R.id.home_profile1_tv);
        textView2=view.findViewById(R.id.home_profile2_tv);
        textView3=view.findViewById(R.id.home3_tv);
        textView4=view.findViewById(R.id.home4_tv);
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        storageReference=firebaseStorage.getReference();
        databaseReference=firebaseDatabase.getReference();
        color_theme=new color_management(getActivity());
        linearLayout1=view.findViewById(R.id.linearlayout1);
        linearLayout2=view.findViewById(R.id.linearlayout2);
        linearLayout3=view.findViewById(R.id.linearlayout3);
        linearLayout4=view.findViewById(R.id.linearlayout4);
          checkThemeColor();
         // checkprofilephoto();
          checkuserandemailnumber();
        //super.onViewCreated(view, savedInstanceState);
    }
    public void checkprofilephoto(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        storageReference.child(userid).child("images/profile pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Picasso.get().load(uri).into(circleImageView1);
               // Picasso.get().load(uri).into(circleImageView2);
               // Picasso.get().load(uri).into(circleImageView3);
               // Picasso.get().load(uri).into(circleImageView4);
            }
        });
    }
    private void checkuserandemailnumber() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        final String userid=firebaseUser.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String username=data.Username.trim();
                String email=data.Email.trim();
                String phone=data.Number.trim();
                 textView1.setText(username);
                 textView3.setText(phone);
                 textView4.setText(email);
                Toast.makeText(getContext(),username+""+email+""+phone,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(userid).child("ProfileDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                //String fullname=data.FullNameData;
                //String email=data.Email;
                String address=data.CityData;
                //textView2.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
