package com.example.faisal.preferred.fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.faisal.preferred.CustomEditText;
import com.example.faisal.preferred.CustomLoader;
import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentProfileBinding;
import com.example.faisal.preferred.other.CustomFragment;
import java.util.ArrayList;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements CustomFragment{

  DashboardActivity parent;
  FragmentProfileBinding dataBinding;
  public CustomLoader loader;

  public ProfileFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.setDrawerLockMode(false);
    parent.setFullScreenMode(false);
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
    CustomEditText Inputs = new CustomEditText();
    Inputs.add(dataBinding.firstName,dataBinding.lastName,dataBinding.email,dataBinding.password);
    loader = new CustomLoader(dataBinding.mProgressView, parent);
    setClickListeners();
    setAdapters();
    initializeData();
    return dataBinding.getRoot();
  }

  public void setClickListeners(){
    dataBinding.male.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dataBinding.male.setBackgroundResource(R.drawable.gender_btn_left_active);
        dataBinding.male.setTextColor(Color.parseColor("#ffffff"));
        dataBinding.female.setBackgroundResource(R.drawable.gender_btn_right);
        dataBinding.female.setTextColor(getResources().getColor(R.color.text_grey));
      }
    });
    dataBinding.female.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dataBinding.male.setBackgroundResource(R.drawable.gender_btn_left);
        dataBinding.male.setTextColor(getResources().getColor(R.color.text_grey));
        dataBinding.female.setBackgroundResource(R.drawable.gender_btn_right_active);
        dataBinding.female.setTextColor(Color.parseColor("#ffffff"));
      }
    });
    dataBinding.save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        UpdateProfileTask task = new UpdateProfileTask();
        task.execute((Void) null);
      }
    });
    dataBinding.changePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dataBinding.password.setTransformationMethod(null);
        dataBinding.password.post(new Runnable() {
          public void run() {
            dataBinding.password.setEnabled(true);
            dataBinding.password.setFocusable(true);
            dataBinding.password.setFocusableInTouchMode(true);
            dataBinding.password.requestFocus();
            InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(parent.INPUT_METHOD_SERVICE);
            lManager.showSoftInput(dataBinding.password, 0);
            dataBinding.password.setSelection(dataBinding.password.getText().length());
          }
        });
      }
    });
    dataBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          dataBinding.password.setEnabled(false);
          dataBinding.password.setTransformationMethod(new PasswordTransformationMethod());
        }
        return handled;
      }
    });
  }

  public void setAdapters(){
    ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(parent, R.layout.custom_spinner_layout
      , getDays());
    DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    dataBinding.day.setAdapter(DayAdapter);

    ArrayAdapter<String> MonthAdapter = new ArrayAdapter<String>(parent, R.layout.custom_spinner_layout
      , getMonths());
    MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    dataBinding.month.setAdapter(MonthAdapter);

    ArrayAdapter<String> YearAdapter = new ArrayAdapter<String>(parent, R.layout.custom_spinner_layout
      , getYears());
    YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    dataBinding.year.setAdapter(YearAdapter);
  }

  public void initializeData(){
    dataBinding.firstName.setText("Mahsam");
    dataBinding.lastName.setText("Abbas");
    dataBinding.email.setText("mahsam@gmail.com");
    dataBinding.password.setText("abc1234");
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

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }

  public class UpdateProfileTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      loader.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
      try {
        // Simulate network access.
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        return false;
      }
      return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
      super.onPostExecute(aBoolean);
      loader.hide();
      Snackbar.make(parent.findViewById(android.R.id.content),"Profile Updated Successfully",Snackbar.LENGTH_LONG).show();
    }
  }
}
