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
        openFirstFragment(0, false);
    }

    private void openFirstFragment(int previousNumber, Boolean clearInputFields) {
        startFragment(FirstFragment.getInstance(previousNumber, clearInputFields));
    }

    private void openSecondFragment(int min, int max) {
        startFragment(SecondFragment.getInstance(min, max));
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onGenerateButtonClicked(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackButtonClicked(int previousNumber) {
        openFirstFragment(previousNumber, true);
    }
}
