package com.example.chat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null){
            firebaseUser = mAuth.getCurrentUser();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(firebaseUser == null){
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);

    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler !=null && runnable != null){
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onBackPressed() {
    Intent i = new Intent(Intent.ACTION_MAIN);
    i.addCategory(Intent.CATEGORY_HOME);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);
    }

 */
}