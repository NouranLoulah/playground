package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nouran.playground.AppIntroSampleSlider;
import com.example.nouran.playground.R;
import com.github.paolorotolo.appintro.AppIntro;


public class WelcomeActivity extends AppIntro {
    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        //adding the three slides for introduction app you can ad as many you needed
        addSlide(AppIntroSampleSlider.newInstance(R.layout.slide1));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.slide2));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.slide3));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.slide4));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(false);

        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        setVibrate(true);
        setVibrateIntensity(50);

        //Add animation to the intro slider
        setDepthAnimation();
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {

        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        Toast.makeText(getApplicationContext(),
                getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }


}
