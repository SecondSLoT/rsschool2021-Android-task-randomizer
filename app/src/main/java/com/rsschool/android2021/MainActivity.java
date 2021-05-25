package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements FirstFragment.Callbacks, SecondFragment.Callbacks {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.getInstance(previousNumber);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, firstFragment)
                .commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, secondFragment)
                .commit();
    }

    @Override
    public void onGenerateButtonClicked(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackButtonClicked(int previousNumber) {
        openFirstFragment(previousNumber);
    }
}
