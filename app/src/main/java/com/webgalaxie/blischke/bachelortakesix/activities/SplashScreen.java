package com.webgalaxie.blischke.bachelortakesix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.webgalaxie.blischke.bachelortakesix.R;

/**
 * Created by Bexx on 20.12.17.
 */

public class SplashScreen  extends AppCompatActivity {

    RelativeLayout l1, l2;
    Animation uptodown, downtoup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup the general layout of the screen
        setContentView(R.layout.splashscreen);

        // get the references to the two seperate layouts for the animation
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);

        // load the animations for the layouts
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        //set the animation to the according layout
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        // add logic for the splashscreen
        // splash screen will change to LoginActivity after 3000 milliseconds
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try{
                    // pause the thread for 3 seconds
                    sleep(3000);
                    // finish the current activity
                    finish();
                    // start the new activity
                    startActivity(new Intent(getApplicationContext(),LoginScreen.class));
                }catch (InterruptedException e){
                    // if there was an error print it out
                    e.printStackTrace();
                }
            }
        };

        // start the thread
        splashThread.start();
    }
}
