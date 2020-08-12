package com.example.easynaukri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynaukri.ui.InternetConnection;
import com.example.easynaukri.ui.slideshow.SlideshowFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApplyJob extends AppCompatActivity {
    TextView tv_title,tv_salary,tv_name,tv_email,tv_previouswork,tv_previousworkexp,tv_address,tv_paymentstatus;
    CircleImageView jobimage;
    Button btn_payment;
    LinearLayout layout;
    color_management color_theme;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    String upiid="paytm-26378481@paytm";
    int amount=299;
    String transid="100";
    String paymentmethod="";
    String statuspa="pending";
    String jobtitle,salary;

    int GOOGLE_PAY_REQUEST_CODE = 123;

    InternetConnection connection=new InternetConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        btn_payment=findViewById(R.id.applyjob_payment);
        tv_title=findViewById(R.id.applyjob_title);
        tv_salary=findViewById(R.id.applyjob_salary);
        jobimage=findViewById(R.id.applyjob_image);
        tv_name=findViewById(R.id.applyjob_name);
        tv_email=findViewById(R.id.applyjob_email);
        tv_previouswork=findViewById(R.id.applyjob_previouswork);
        tv_previousworkexp=findViewById(R.id.applyjob_previousworkexpe);
        tv_address=findViewById(R.id.applyjob_address);
        tv_paymentstatus=findViewById(R.id.applyjob_paymentstatus);
        layout=findViewById(R.id.applyjob_back);
        color_theme=new color_management(this);
        checkBonusData();
        getSupportActionBar().setTitle("Applying Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
        getUserData();
        checkThemeColor();
        Bundle bundle=getIntent().getExtras();
         jobtitle=bundle.getString("title");
        int imageid=bundle.getInt("image");
          salary=bundle.getString("salary");
       // int image=Integer.parseInt(imageid);
        tv_title.setText("Applying For: "+jobtitle);
        tv_salary.setText("Salary: "+salary);
        jobimage.setImageResource(imageid);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDialog();
                showCustomDialog();
            }
        });

    }
    public void getUserData(){
        String userid=firebaseUser.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String username=data.Username;
                String email=data.Email;
                tv_email.setText("Email: "+email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(userid).child("ProfileDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String fullname=data.FullNameData;
                //String email=data.Email;
                String address=data.CityData;
                String work=data.WorkData;
                String workexp=data.WorkExperienceData;
                tv_name.setText("FullName: "+fullname);
                tv_previouswork.setText("PreviousWork: "+work);
                tv_previousworkexp.setText("PreviousworkExperience: "+workexp);
                tv_address.setText("Location: "+address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(userid).child("PaymentDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PaymentDetails details=dataSnapshot.getValue(PaymentDetails.class);
                String status=details.Status;
                tv_paymentstatus.setText("PaymentStatus: "+status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        String[] methods={"GooglePay","AllInOne"};
        builder.setTitle("Choose Payment Method");
        builder.setCancelable(false);
        builder.setItems(methods, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Toast.makeText(getApplicationContext(),"googlepay",Toast.LENGTH_LONG).show();
                   // payUsingUpi("googlepay");

                }
                else if(which==1){
                    Toast.makeText(getApplicationContext(),"all in one",Toast.LENGTH_LONG).show();
                    payUsingPaytm();
                }
            }
        });
        builder.create().show();
    }
    public void showCustomDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.paymentmethod,null);
        builder.setView(view);
        builder.setCancelable(false);
        Button googlepay=view.findViewById(R.id.alertgooglepay);
        Button allinone=view.findViewById(R.id.alertallinone);
        Button canceldialog=view.findViewById(R.id.custom_canceldialog);
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        canceldialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        googlepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi("googlepay");
                dialog.cancel();

            }
        });
        allinone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingPaytm();
                dialog.cancel();
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
        Intent intent = new Intent(getApplicationContext(), checksum.class);
        intent.putExtra("orderid",OrderId );
        intent.putExtra("custid", CustId);
        intent.putExtra("amount",amount);
        intent.putExtra("jobtitle",jobtitle);
        intent.putExtra("salary",salary);
        startActivity(intent);
    }
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
                        .appendQueryParameter("am", String.valueOf(amount))
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        paymentmethod="GooglePay";
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

// will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

// check if intent resolves
        if(null != chooser.resolveActivity(getApplicationContext().getPackageManager())) {
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }
    public void upiPaymentDataOperation(ArrayList<String> data) {
        if (connection.connected(getApplicationContext())) {
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
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                Random r=new Random();
                int generated=r.nextInt(1000);
                DatabaseReference updateapply=database.getReference().child(useruid).child("AppiledFor").child(jobtitle);
                HashMap<String,String> apply=new HashMap<>();
                apply.put("Salary",salary);
                apply.put("Status","Success");
                apply.put("DateTime",strDate+strTime);
                updateapply.setValue(apply);
                DatabaseReference databaseReference=database.getReference().child("TotalPayment").child(useruid).child(generated+strDate+strTime);
                HashMap<String,String> res=new HashMap<>();
                res.put("Status","Success");
                res.put("Amount",String.valueOf(amount));
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
                DatabaseReference updateapply=database.getReference().child(useruid).child("AppiledFor").child(jobtitle);
                HashMap<String,String> apply=new HashMap<>();
                apply.put("Salary",salary);
                apply.put("Status","Pending");
                apply.put("DateTime",strDate+strTime);
                updateapply.setValue(apply);
                PaymentDetails details=new PaymentDetails(amount,transid,strDate,strTime,paymentmethod,cancel,payment,statuspa);
                myRef.setValue(details);
                Toast.makeText(getApplicationContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
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
                DatabaseReference updateapply=database.getReference().child(useruid).child("AppiledFor").child(jobtitle);
                HashMap<String,String> apply=new HashMap<>();
                apply.put("Salary",salary);
                apply.put("Status","Pending");
                apply.put("DateTime",strDate+strTime);
                updateapply.setValue(apply);
                PaymentDetails details=new PaymentDetails(amount,transid,strDate,strTime,paymentmethod,cancel,payment,statuspa);
                myRef.setValue(details);
                Toast.makeText(getApplicationContext(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                // Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Toast.makeText(getApplicationContext(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void checkBonusData(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid).child("ReferEarn");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object bonus=snapshot.child("ActualWallet").getValue();
                if(bonus.toString().equals("50")){
                    amount=249;
                }
                else if(bonus.toString().equals("100")){
                    amount=199;
                }
                else if(bonus.toString().equals("150")){
                    amount=149;
                }
                else if(bonus.toString().equals("0")){
                    amount=299;
                }
                else{
                    amount=149;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

            layout.setBackgroundResource(R.drawable.blue_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_grid_back));
        }
        else if(color.equals(red)){
            layout.setBackgroundResource(R.drawable.red_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
        }
        else if(color.equals(green)){
            layout.setBackgroundResource(R.drawable.green_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.green_gradient_themea));
        }
        else if(color.equals(orange)){

            layout.setBackgroundResource(R.drawable.orange_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_gradient_themea));

        }
        else if(color.equals(yellow)){

            layout.setBackgroundResource(R.drawable.yellow_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_gradient_themea));
        }
        else if(color.equals(dark)){

            layout.setBackgroundResource(R.drawable.dark_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_gradient_themea));

        }
        else if(color.equals(brown)){

            layout.setBackgroundResource(R.drawable.brown_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.brown_gradient_themea));

        }
    }
  public void updateApplyFor(){

  }
}