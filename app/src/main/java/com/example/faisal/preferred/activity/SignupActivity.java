package com.example.faisal.preferred.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.faisal.preferred.CustomEditText;
import com.example.faisal.preferred.R;
import com.example.faisal.preferred.databinding.ActivitySignupBinding;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {
  ActivitySignupBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
    CustomEditText Inputs = new CustomEditText();
    Inputs.add(binding.firstName,binding.lastName,binding.email,binding.password,binding.referral);
    setFacebookCallbacks();
    setClickListeners();
    setAdapters();
  }

  public void setClickListeners(){
    binding.facebookLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(SignupActivity.this, Arrays.asList("public_profile"));
      }
    });
    binding.male.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.male.setBackgroundResource(R.drawable.gender_btn_left_active);
        binding.male.setTextColor(Color.parseColor("#ffffff"));
        binding.female.setBackgroundResource(R.drawable.gender_btn_right);
        binding.female.setTextColor(getResources().getColor(R.color.text_grey));
      }
    });
    binding.female.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.male.setBackgroundResource(R.drawable.gender_btn_left);
        binding.male.setTextColor(getResources().getColor(R.color.text_grey));
        binding.female.setBackgroundResource(R.drawable.gender_btn_right_active);
        binding.female.setTextColor(Color.parseColor("#ffffff"));
      }
    });
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
      }
    });
    binding.signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(SignupActivity.this, VerificationActivity1.class));
        finish();
      }
    });
  }

  public void setAdapters(){
    ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout
      , getDays());
    DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    binding.day.setAdapter(DayAdapter);

    ArrayAdapter<String> MonthAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout
      , getMonths());
    MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    binding.month.setAdapter(MonthAdapter);

    ArrayAdapter<String> YearAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout
      , getYears());
    YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    binding.year.setAdapter(YearAdapter);
  }

  public void setFacebookCallbacks(){
    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().registerCallback(callbackManager,
      new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException exception) {

        }
      }
    );
  }

  ArrayList getDays(){
    ArrayList DAYS = new ArrayList();
    for(int i=1 ; i<=31 ; i++){
      if (i < 10)
        DAYS.add("0" + i);
      else
        DAYS.add("" + i);
    }

    return DAYS;
  }

  ArrayList getMonths(){
    ArrayList MONTHS = new ArrayList();
    for(int i=1 ; i<=12 ; i++){
      if (i < 10)
        MONTHS.add("0" + i);
      else
        MONTHS.add("" + i);
    }
    return MONTHS;
  }

  ArrayList getYears(){
    ArrayList YEARS = new ArrayList();
    int limit = Calendar.getInstance().get(Calendar.YEAR) - 10;
    for(int i=1950 ; i<=limit ; i++){
      if (i < 10)
        YEARS.add("0" + i);
      else
        YEARS.add("" + i);
    }
    return YEARS;
  }


}
