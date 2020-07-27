package com.example.easynaukri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    //Animation splash_top,splash_bottom;
   ImageView splash;
   TextView splash_bot;
   SessionMangament sessionMangament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        sessionMangament=new SessionMangament(splash_screen.this);
        // splash_top=AnimationUtils.loadAnimation(this,R.anim.splash_top);
        //splash_bottom=AnimationUtils.loadAnimation(this,R.anim.splash_bottom);
        splash=findViewById(R.id.splash_image);
        splash_bot=findViewById(R.id.splash_bottom);
        //splash.setAnimation(splash_top);
        //splash_bot.setAnimation(splash_bottom);
        Thread td=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    if(!sessionMangament.getsession()) {
                        Intent i = new Intent(splash_screen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        startActivity(new Intent(splash_screen.this, logged_in_page.class));
                        finish();

                    }
                }
            }
        };
        td.start();
    }

}
