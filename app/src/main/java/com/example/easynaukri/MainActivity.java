package com.example.easynaukri;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.easynaukri.ui.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
     database_helper database_helper=new database_helper(this);
     boolean checktoverify;
     EditText Email,Password;
     String userName,sendPassword;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    String EMAIL,PASSWORD,USERNAME,NUMBER;
    SessionMangament sessionMangament;
     Button btnbackground;
     InternetConnection connection;
    ProgressDialog internetdialog;

    private void checksession() {
        if(sessionMangament.getsession()){
            startActivity(new Intent(MainActivity.this,profile_details.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        connection=new InternetConnection();
        internetdialog=new ProgressDialog(this);
        internetdialog.setTitle("Internet Issue");
        internetdialog.setMessage("Turn On Internet");
        checkInternet();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        sessionMangament=new SessionMangament(MainActivity.this);
        checksession();
        final Button login=(Button)findViewById(R.id.login);
        Button forgotpass=(Button)findViewById(R.id.forgotpassword);
        final Button signup=findViewById(R.id.signup);
        Email= findViewById(R.id.EMAIL);
        Password= findViewById(R.id.PASSWORD);
        USERNAME=getIntent().getStringExtra("UserName");
        EMAIL=getIntent().getStringExtra("Email");
        PASSWORD=getIntent().getStringExtra("Password");
        NUMBER=getIntent().getStringExtra("Number");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,Forgot_password.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int answer = checkempty(Email.getText().toString(), Password.getText().toString());


                        if (answer == 2) {
                            progressDialog.setMessage("Wait for some seconds");
                            progressDialog.show();
                             signinuser(Email.getText().toString().trim(),Password.getText().toString().trim());

                             //startActivity(new Intent(MainActivity.this,profile_details.class));


                           /* String  result = validate_input(Email.getText().toString(), Password.getText().toString());
                            if (result.equals(Password.getText().toString())) {
                               /* Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                signinuser(Email.getText().toString().trim(),Password.getText().toString().trim());
                                Intent i=new Intent(MainActivity.this,logged_in_page.class);
                                userName=Email.getText().toString();
                                sendPassword=Password.getText().toString();
                                i.putExtra("NAME",userName);
                                i.putExtra("PASSWORD",sendPassword);
                                startActivity(i)
                               verifyemail();


                            } else
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

                            */
                        }



                    }
                }
        );

    }

    private void checkInternet() {
        if(connection.connected(getApplicationContext())){
            internetdialog.cancel();
            Toast.makeText(this,"Internet is Active",Toast.LENGTH_SHORT).show();
        }
        else
        {
             internetdialog.show();
        }
    }

    public String validate_input(String email,String password) {
            String data="Email or Password is wrong";
            Cursor answer = database_helper.showdata();
            while (answer.moveToNext()) {
                String EMAIL = answer.getString(answer.getColumnIndex("email"));
                String PASSWORD = answer.getString(answer.getColumnIndex("password"));
                if (EMAIL.equals(email) && PASSWORD.equals(password)) {
                   userName=EMAIL;
                    return PASSWORD;
                }
            }
            return data;
        }
        public Integer checkempty(String email,String password){
         if(email.equals("")) {
             //Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
             Email.setError("Email Cannot be empty");
             return 0;
         }
         else if(password.equals("")) {
            // Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
             Password.setError("Password cannot be empty");
             return 1;
         }
         else
             return 2;
        }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("CLOSE");
        builder.setMessage("Do You Want To close the application");
        builder.setCancelable(false);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                finish();
                //super.onBackPressed();
            }
        });
       AlertDialog dialog=builder.create();
       dialog.show();
    }
    public void signinuser(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Intent i=new Intent(MainActivity.this,logged_in_page.class);
                    //startActivity(i);
                   // progressDialog.cancel();
                    checktoverify=true;
                    //Toast.makeText(MainActivity.this,"")
                    verifyemail();
                }
                else{
                    progressDialog.cancel();
                    checktoverify=false;
                    Toast.makeText(getApplicationContext(),"check ur email and password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void verifyemail() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
            boolean user = firebaseUser.isEmailVerified();
            if (user) {
               // adddatatofirebase();
                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, profile_details.class);
                 userName = Email.getText().toString();
                 sendPassword = Password.getText().toString();
                 sessionMangament.setsession(true);
                //i.putExtra("NAME", userName);
                //i.putExtra("PASSWORD", sendPassword);
                finishAffinity();
                finish();
                startActivity(i);
            } else {
                progressDialog.cancel();
                Toast.makeText(MainActivity.this, "Verify Your Email", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }



    }
    public void goToMainActivity(){
        Intent i = new Intent(MainActivity.this,logged_in_page.class);
        startActivity(i);
    }

}
