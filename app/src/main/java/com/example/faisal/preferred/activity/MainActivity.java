package com.example.faisal.preferred.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.faisal.preferred.PrefManager;
import com.example.faisal.preferred.R;

public class MainActivity extends AppCompatActivity {
  private PrefManager prefManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Checking for first time launch - before calling setContentView()
    prefManager = new PrefManager(this);
    if (prefManager.isFirstTimeLaunch()) {
      startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
      finish();
    }
    setContentView(R.layout.activity_main);
  }

}
