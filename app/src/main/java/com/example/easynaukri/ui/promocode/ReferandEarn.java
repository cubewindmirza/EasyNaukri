package com.example.easynaukri.ui.promocode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynaukri.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReferandEarn extends Fragment {
    TextView wallet,promocode,actualwallet;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ReferandEarnViewModel mViewModel;
    Button check;
    int amount=0;
    String userPromocode;

    public static ReferandEarn newInstance() {
        return new ReferandEarn();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.referand_earn_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReferandEarnViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Refer And Earn");
        wallet=view.findViewById(R.id.referwalletbalance);
        actualwallet=view.findViewById(R.id.actualwalletbalance);
        promocode=view.findViewById(R.id.refer_promocode);
        check=view.findViewById(R.id.ReferNow);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        checkWalletandCode();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToFind();
            }
        },3);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="EasyNaukri";
                String content=promocode.getText().toString();
                String titleAndContent=title+"\n PromoCode: "+content;
                String full=titleAndContent+"\n"+"Enter Above PromoCode in Easy Naukri App While Signing Up to get Reward of 50 Rupees";
                Intent intentShare = new Intent();
                intentShare.setAction(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT,full);
                startActivity(intentShare);
            }
        });


    }
    public void checkWalletandCode(){
        firebaseUser=firebaseAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        databaseReference.child(userid).child("ReferEarn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object code=dataSnapshot.child("PromoCode").getValue();
                Object balance=dataSnapshot.child("WalletBalance").getValue();
                promocode.setText(code.toString());
                wallet.setText(balance.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
  public void startToFind(){
        userPromocode=promocode.getText().toString();
        DatabaseReference ref=firebaseDatabase.getReference().child("PromoCodes").child(userPromocode).child("From").child("uid");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passingAllUid(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

  }
  public void passingAllUid(DataSnapshot dataSnapshot){
     // StringBuffer buffer=new StringBuffer();
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            checkingPaymentOnebyOne(ds.getKey());
        }
  }
  public void checkingPaymentOnebyOne(String uid){
        DatabaseReference payment=firebaseDatabase.getReference().child(uid).child("PaymentDetails");
        payment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Object status=  snapshot.child("Status").getValue();
                printStatus(status.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
  }
  public void printStatus(String status){
        if(status.equals("Success")) {
            amount=amount+50;
            actualwallet.setText(String.valueOf(amount));
            updateActualWallet();
            //Toast.makeText(getContext(), String.valueOf(amount), Toast.LENGTH_SHORT).show();
        }
  }
  public void updateActualWallet(){
      firebaseUser=firebaseAuth.getCurrentUser();
      String userid=firebaseUser.getUid();
        DatabaseReference update=firebaseDatabase.getReference().child(userid).child("ReferEarn");
        update.child("ActualWallet").setValue(String.valueOf(amount));
  }
}