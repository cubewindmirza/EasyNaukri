package com.example.easynaukri.ui.slideshow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easynaukri.PaymentDetails;
import com.example.easynaukri.R;
import com.example.easynaukri.UserProfileData;
import com.example.easynaukri.checksum;
import com.example.easynaukri.logged_in_page;
import com.example.easynaukri.ui.InternetConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class SlideshowFragment extends Fragment {
   Button googlepay,paytm,phonepay;
   //EditText ed_UpiId;
   TextView tv_Name,tv_Method,tv_Amount,tv_Transid,tv_Date,tv_Time,tv_PaymentStatus;

    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    String PHONE_PAY_PACKAGE_NAME = "com.phonepe.app";
    String upiid="paytm-26378481@paytm";
    String amount="299";
    String transid="100";
    String paymentmethod="";
    String statuspa="pending";

    FirebaseAuth firebaseAuth; int GOOGLE_PAY_REQUEST_CODE = 123;

    InternetConnection connection=new InternetConnection();
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);
        //final TextView textView = root.findViewById(R.id.textview);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        googlepay=view.findViewById(R.id.googlepay);
        paytm=view.findViewById(R.id.paytm);
       // phonepay=view.findViewById(R.id.phonepay);
        tv_Name=view.findViewById(R.id.paymentusername);
        tv_Amount=view.findViewById(R.id.paymentamount);
        tv_Transid=view.findViewById(R.id.paymenttransid);
        tv_Date=view.findViewById(R.id.paymentdate);
        tv_Time=view.findViewById(R.id.paymenttime);
        tv_Method=view.findViewById(R.id.paymentmethod);
        tv_PaymentStatus=view.findViewById(R.id.paymentstatus);
        checkPaymentDone();
        //ed_UpiId=view.findViewById(R.id.upiid);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Payment");
        showReceipt();
        googlepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi("googlepackage");
            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payUsingUpi("paytmpackage");
                payUsingPaytm();
            }
        });
        /*phonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //payUsingUpi("phonepaypackage");
            }
        });

         */
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {
            if ((RESULT_OK == resultCode) || (resultCode == GOOGLE_PAY_REQUEST_CODE)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    //Log.e("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    // Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                //when user simply back without payment
                // Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    public void payUsingUpi(String nameofpackage){
        Calendar c = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        transid="85538"+sdf.format(c.getTime())+"3750";
        /*if(!ed_UpiId.getText().toString().equals("")){
            upiid=ed_UpiId.getText().toString();
        }

         */
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", upiid)
                        .appendQueryParameter("pn", "mirza")
                        .appendQueryParameter("mc", "")
                        .appendQueryParameter("tr", transid)
                        .appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if(nameofpackage.equals("googlepackage")){
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            paymentmethod="GooglePay";
        }
        else if(nameofpackage.equals("paytmpackage"))
        {
            intent.setPackage(PAYTM_PACKAGE_NAME);
            paymentmethod="Paytm";
        }
        else if(nameofpackage.equals("phonepaypackage")){
            intent.setPackage(PHONE_PAY_PACKAGE_NAME);
            paymentmethod="PhonePay";
        }
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

// will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

// check if intent resolves
        if(null != chooser.resolveActivity(getActivity().getPackageManager())) {
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            Toast.makeText(getActivity(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }
    public void upiPaymentDataOperation(ArrayList<String> data) {
        if (connection.connected(getActivity())) {
            String str = data.get(0);
           // Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
                String strDate = sdfdate.format(c.getTime());
                String strTime = sdftime.format(c.getTime());
                String payment="Success";
                String cancel="NO";
                /*result.put("Payment",payment);
                result.put("Date",strDate);
                result.put("Time",strTime);
                result.put("TransactionId",transid);
                result.put("PaymentMethod",paymentmethod);

                 */
                statuspa="Success";
                PaymentDetails details=new PaymentDetails(amount,transid,strDate,strTime,paymentmethod,cancel,payment,statuspa);
                myRef.setValue(details);
                Toast.makeText(getActivity(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                DatabaseReference databaseReference=database.getReference().child("TotalPayment").child(useruid);
                Random r=new Random();
                int generated=r.nextInt(1000);
                HashMap<String,String> res=new HashMap<>();
                res.put(generated+strDate+strTime,"Paid");
                databaseReference.setValue(res);
                // Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
                String strDate = sdfdate.format(c.getTime());
                String strTime = sdftime.format(c.getTime());
                String payment="Not Done";
                String cancel="Canceled by user";
                /*result.put("Payment",payment);
                result.put("Cancel",cancel);
                result.put("Date",strDate);
                result.put("Time",strTime);
                result.put("TransactionId",transid);
                result.put("PaymentMethod",paymentmethod);

                 */
                PaymentDetails details=new PaymentDetails(amount,transid,strDate,strTime,paymentmethod,cancel,payment,statuspa);
                myRef.setValue(details);
                Toast.makeText(getActivity(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                // Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
                String strDate = sdfdate.format(c.getTime());
                String strTime = sdftime.format(c.getTime());
                String cancel="Some error while transation";
                String payment="Not Done";
                /*String payment="Not Done";
                String cancel="Some error while transation";
                result.put("Payment",payment);
                result.put("Cancel",cancel);
                result.put("Date",strDate);
                result.put("Time",strTime);
                result.put("TransactionId",transid);
                result.put("PaymentMethod",paymentmethod);

                 */
                PaymentDetails details=new PaymentDetails(amount,transid,strDate,strTime,paymentmethod,cancel,payment,statuspa);
                myRef.setValue(details);
                Toast.makeText(getActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                // Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            //Log.e("UPI", "Internet issue: ");

            Toast.makeText(getActivity(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public void showReceipt(){
            firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            String useruid = firebaseUser.getUid();
            DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   PaymentDetails details=dataSnapshot.getValue(PaymentDetails.class);
                   String amount=details.Amount;
                   String transid=details.TransactionId;
                   String date=details.Date;
                   String time=details.Time;
                   String paymentmethod=details.PaymentMethod;
                   String status=details.Status;
                   tv_Amount.setText(amount);
                   tv_Transid.setText(transid);
                   tv_Date.setText(date);
                   tv_Time.setText(time);
                   tv_Method.setText(paymentmethod);
                   tv_PaymentStatus.setText(status);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child(useruid).child("ProfileDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String fullname=data.FullNameData;
                tv_Name.setText(fullname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void payUsingPaytm(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfdate = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat sdftime = new SimpleDateFormat(" HHmmss");
        String strOrderId = sdfdate.format(c.getTime())+c.getTimeInMillis();
        String strCustId = sdftime.format(c.getTime())+c.getTimeInMillis();
        Random r=new Random();
       int generated1= r.nextInt(100000);
        int generated2= r.nextInt(100000);
        String OrderId=generated1+strOrderId;
        String CustId=generated2+strCustId;
        Intent intent = new Intent(getContext(), checksum.class);
        intent.putExtra("orderid",OrderId );
        intent.putExtra("custid", CustId);
        startActivity(intent);
    }
    public void checkPaymentDone(){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PaymentDetails details=snapshot.getValue(PaymentDetails.class);
                String checkstatus=details.Status;
                String checkpayment=details.Payment;
                if(checkpayment.equals("Success")) {
                  if(checkstatus.equals("Success")){
                      Toast.makeText(getContext(),"You Have Doned The Payment",Toast.LENGTH_LONG).show();
                      googlepay.setEnabled(false);
                      paytm.setEnabled(false);
                  }
                  else{
                      googlepay.setEnabled(true);
                      paytm.setEnabled(true);
                  }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}