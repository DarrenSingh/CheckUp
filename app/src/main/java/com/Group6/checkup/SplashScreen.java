package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.img_logo);

        /* Animation will load the animation effect created in the
          transition.xml file*/
        Animation animation = AnimationUtils.loadAnimation
                (this, R.anim.transition);
        logo.startAnimation(animation);

        //Timertask to end the SplashScreen activity and start MainActivity.
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreen.this,
                        LoginActivity.class));
            }
        };

        // timer will schedule the MainActivity after a delay of 5seconds.
        Timer time = new Timer();
        time.schedule(task, 2500);

    }
}
