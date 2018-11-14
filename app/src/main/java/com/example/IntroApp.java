package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;

public class IntroApp extends AppIntro2 {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_step_1));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_step_2));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_step_3));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_step_4));
        showSkipButton(false);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
