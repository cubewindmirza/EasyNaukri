package com.example.easynaukri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class profile_details extends AppCompatActivity  {
    EditText profile_FullName,profile_State,profile_FatherName,profile_MotherName,profile_Address,profile_PinCode,profile_City,profile_Dob,profile_Work,profile_Experience,profile_PromoCode;
    Button profile_Update;
    RadioGroup profile_Gender;
    RadioButton profile_Check;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SessionMangament sessionMangament;
    ProgressDialog dialog;
    String checking="false";
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        getSupportActionBar().hide();
        sessionMangament=new SessionMangament(this);
        dialog=new ProgressDialog(this);
        checksession();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        profile_FullName=findViewById(R.id.profile_fullname);
        profile_FatherName=findViewById(R.id.profile_fathername);
        profile_MotherName=findViewById(R.id.profile_mothername);
        profile_Address=findViewById(R.id.profile_address);
        profile_PinCode=findViewById(R.id.profile_pincode);
        profile_Gender=findViewById(R.id.profile_gender);
        profile_Update=findViewById(R.id.profile_update);
        profile_City=findViewById(R.id.profile_city);
        profile_State=findViewById(R.id.profile_state);
        profile_Dob=findViewById(R.id.profile_dob);
        profile_Work=findViewById(R.id.profile_skill);
        profile_Experience=findViewById(R.id.profile_skillexperience);
        profile_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                final int mYear = calendar.get(Calendar.YEAR); // current year
                final int mMonth = calendar.get(Calendar.MONTH); // current month
                final int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(profile_details.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                profile_Dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        profile_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUserData()) {
                    update_UserData();
                    update_PaymentDetails();
                    sessionMangament.setsessionprofile(true);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference((FirebaseAuth.getInstance().getUid()));
                    HashMap<String,Object> result=new HashMap<String,Object>();
                    String session="true";
                    result.put("SessionData",session);
                    myRef.updateChildren(result);
                    gotologgedpage();
                }
            }
        });
    }
   public void update_UserData(){
       String fullname=profile_FullName.getText().toString().trim();
       String fathername=profile_FatherName.getText().toString().trim();
       String mothername=profile_MotherName.getText().toString().trim();
       String dob=profile_Dob.getText().toString().trim();
       String address=profile_Address.getText().toString().trim();
       String state=profile_State.getText().toString().trim();
       String city=profile_City.getText().toString().trim();
       String pincode=profile_PinCode.getText().toString().trim();
       String work=profile_Work.getText().toString().trim();
       String workexperience=profile_Experience.getText().toString().trim();
       int genderid=profile_Gender.getCheckedRadioButtonId();
       profile_Check=findViewById(genderid);
       String gender=profile_Check.getText().toString().trim();
       UserProfileData userProfileData=new UserProfileData(fullname,fathername,mothername,dob,address,state,city,pincode,gender,work,workexperience);
       DatabaseReference profile_data=databaseReference.child(firebaseUser.getUid()).child("ProfileDetails");
     /*  HashMap<String,Object> result=new HashMap<>();
       result.put("FullName",fullname);
       result.put("FatherName",fathername);
       result.put("MotherName",mothername);
       result.put("Dob",dob);
       result.put("Address",address);
       result.put("State",state);
       result.put("City",city);
       result.put("PinCode",pincode);
       result.put("Gender",gender);

      */
       profile_data.setValue(userProfileData);
   }
   public boolean checkUserData(){
       String fullname=profile_FullName.getText().toString().trim();
       String fathername=profile_FatherName.getText().toString().trim();
       String mothername=profile_MotherName.getText().toString().trim();
       String dob=profile_Dob.getText().toString().trim();
       String address=profile_Address.getText().toString().trim();
       String state=profile_State.getText().toString().trim();
       String city=profile_City.getText().toString().trim();
       String pincode=profile_PinCode.getText().toString().trim();
       String work=profile_Work.getText().toString().trim();
       String workexperience=profile_Experience.getText().toString().trim();
       boolean check=false;
       if(fullname.equals("")){
           profile_FullName.setError("Please Fill Your FullName");
       }
       else if(fathername.equals("")){
           profile_FatherName.setError("Please Fill FatherName");
       }
       else if(mothername.equals("")){
           profile_MotherName.setError("Please Fill MotherName");
       }
       else if(dob.equals("")){
           profile_Dob.setError("Please Fill Dob");
       }
       else if(address.equals("")){
           profile_Address.setError("Please Fill The Address");
       }
       else if(state.equals("")){
           profile_State.setError("Please Fill The state");
       }
       else if(city.equals("")){
           profile_City.setError("Please Fill The City");
       }
       else if(pincode.equals("")){
           profile_PinCode.setError("Please Fill The Pincode");
       }
       else if(work.equals("")){
           profile_Work.setError("Please Enter Your Work");
       }
       else if(workexperience.equals("")){
           profile_Experience.setError("Please Enter Work Experience");
       }
       else
           check=true;
       return check;
   }
    private void checksession() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                checking=data.SessionData;
                checksession2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void checksession2(){
        if(checking.equals("true")){
            gotologgedpage();
        }
    }
  public void gotologgedpage(){
        dialog.setTitle("Checking");
        dialog.setMessage("Wait");
        dialog.show();
      Intent i=new Intent(profile_details.this,logged_in_page.class);
      startActivity(i);
  }

public void update_PaymentDetails(){
    firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String useruid = firebaseUser.getUid();
    DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
    HashMap<String,Object> result=new HashMap<>();
    result.put("Amount",299);
    result.put("Cancel","");
    result.put("Date","");
    result.put("Payment","Pending");
    result.put("PaymentMethod","Pending");
    result.put("Time","");
    result.put("TranscationId","");
    result.put("Status","Pending");
    myRef.setValue(result);
}


}