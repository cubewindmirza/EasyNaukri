package com.example.easynaukri;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    database_helper db = new database_helper(this);
    EditText email,password,confirm_password,number,username;
    Button signup,login,getdata;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
   // FirebaseDatabase firebaseDatabase;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z0-9])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //getSupportActionBar().hide();
         firebaseAuth=FirebaseAuth.getInstance();
         progressDialog=new ProgressDialog(this);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
        email = findViewById(R.id.email);
         password = findViewById(R.id.password);
         confirm_password = findViewById(R.id.confirm_password);
         number = findViewById(R.id.Number);
         login = findViewById(R.id.login);
         signup = findViewById(R.id.SIGNUP);
         username=findViewById(R.id.UserName);
        // getdata=findViewById(R.id.GETDATA);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);
                //finish();
            }
        });
       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              validate();
           }
       });
       /*getdata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getalldata();
           }
       });*/

    }
    public void adddata(){
                        String USERNAME=username.getText().toString();
                        String EMAIL=email.getText().toString();
                        String PASSWORD=confirm_password.getText().toString();
                        String NUMBER=number.getText().toString();
                         boolean answer=db.insertdata(USERNAME,EMAIL,PASSWORD,NUMBER);
                        if(answer) {
                           // Toast.makeText(Main2Activity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Main2Activity.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }
           //Toast.makeText(this,EMAIL,Toast.LENGTH_SHORT).show();

    }
    public  void getalldata()
    {
        Cursor cursor=db.showdata();
       /* if(cursor.getCount()==0) {
            showmessage("Error","Nothing");
         return;
        }*/
       StringBuffer buffer=new StringBuffer();
        while (cursor.moveToNext()){
            buffer.append("email: "+cursor.getString(1)+"\n");
            buffer.append("password: "+cursor.getString(2)+"\n");
            buffer.append("number: "+cursor.getString(3)+"\n");


        }
        showmessage("Data",buffer.toString());

    }
    public void showmessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
        public void validate() {
            String UserName=username.getText().toString();
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String Confirm_Password = confirm_password.getText().toString().trim();
            String Number = number.getText().toString();
            if (UserName.equals("")) {
                // Toast.makeText(this,"UserName cannot be empty",Toast.LENGTH_SHORT).show();
                username.setError("Username Cannot be empty");
            }
            else if(UserName.length()<6){
                username.setError("Enter Atleast 6 character");
            }
             else if (Email.equals("")) {
                //Toast.makeText(Main2Activity.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                email.setError("Email cannot be empty");
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                email.setError("Please enter a valid email address");
            }
           else  if (Password.equals("")) {
                //Toast.makeText(Main2Activity.this, "Password Field cannot be Empty", Toast.LENGTH_SHORT).show();
                 password.setError("password cannot be empty");
            }
           /* else if(!PASSWORD_PATTERN.matcher(Password).matches()){
                if(password.length()<4)
                    password.setError("Enter atleast 4 characters");
                else
                     password.setError("Enter Atleast one special character");
            }*/
            else if (Confirm_Password.equals("")) {
                //Toast.makeText(Main2Activity.this, "Confirm Password Field cannot be empty", Toast.LENGTH_SHORT).show();
                confirm_password.setError("Confirm Password cannot be empty");
            }
            else if(!Password.equals(Confirm_Password))
                //Toast.makeText(Main2Activity.this,"Confirm Password is not same as password",Toast.LENGTH_LONG).show();
                confirm_password.setError("Confirm password is not same as password");
            else if (Number.equals("")) {
                //Toast.makeText(Main2Activity.this, "Number Cannot Be Empty", Toast.LENGTH_SHORT).show();
                number.setError("Number cannot be empty");
            }
               else if(Number.length()<10){
                   number.setError("Phone number cannot be less than 10 digits");
            }
                else {
                   /* boolean mailchecking=db.checkmail(email.getText().toString());
                    if(!mailchecking)
                    {
                        firebasedata(Email,Password);
                        adddata();

                    }
                    else
                        email.setError("Email already registered");*/
                   progressDialog.setMessage("Wait");
                   progressDialog.show();
                  firebasedata(Email,Password);
                   //adddata();
            }

        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void firebasedata(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    adddatatofirebase();
                    //Toast.makeText(Main2Activity.this,"Successfully registered in firebase",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    sendemailverification();
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(Main2Activity.this, "Please provide the email and password correctly", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }
    public void sendemailverification() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(Main2Activity.this, "Sucessfully registered and Email Verification has been sent", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        Intent i = new Intent(Main2Activity.this, MainActivity.class);
                        i.putExtra("UserName", username.getText().toString());
                        i.putExtra("Email", email.getText().toString());
                        i.putExtra("Password", password.getText().toString());
                        i.putExtra("Number", number.getText().toString());
                        startActivity(i);
                    } else
                        Toast.makeText(Main2Activity.this, "Some error with network", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
            public void adddatatofirebase () {
       /* DatabaseReference databaseReference=firebaseDatabase.getReference(mAuth.getUid());
        userdata userdata=new userdata(USERNAME,EMAIL,PASSWORD,NUMBER);
        databaseReference.setValue(userdata);

        */
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference((firebaseAuth.getUid()));
                String Session="false";
                UserProfileData userdata = new UserProfileData(username.getText().toString(), email.getText().toString(), password.getText().toString(), number.getText().toString(),Session);
                myRef.setValue(userdata);
            }



}
