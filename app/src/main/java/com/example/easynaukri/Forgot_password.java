package com.example.easynaukri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Forgot_password extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button backlogin,reset;
    EditText emailet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
        emailet=findViewById(R.id.emailreset);
         backlogin=findViewById(R.id.back_login);
         reset=findViewById(R.id.submitemail);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Forgot_password.this,MainActivity.class);
                startActivity(i);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailet.equals("")) {
                    passwordreset();
                }
                else
                    emailet.setError("Email cannot be empty");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void passwordreset(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            firebaseAuth.sendPasswordResetEmail(emailet.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Forgot_password.this, "Password reset has sent to ur email", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Forgot_password.this, MainActivity.class));
                    }
                    else
                        Toast.makeText(Forgot_password.this, "Some error while sending reseting passowrd", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
