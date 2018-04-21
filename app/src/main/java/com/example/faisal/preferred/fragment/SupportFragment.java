package com.example.faisal.preferred.fragment;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentProfileBinding;
import com.example.faisal.preferred.databinding.FragmentSupportBinding;
import com.example.faisal.preferred.models.Payment;
import com.example.faisal.preferred.other.CustomFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements CustomFragment{

  DashboardActivity parent;
  FragmentSupportBinding dataBinding;

  public SupportFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.setDrawerLockMode(false);
    parent.setFullScreenMode(false);
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_support, container, false);
    setAdapter();
    return dataBinding.getRoot();
  }

  public void setAdapter(){
    ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(parent, R.layout.custom_spinner_layout
      , getItems());
    DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    dataBinding.messageType.setAdapter(DayAdapter);
  }

  public ArrayList getItems(){
    ArrayList items = new ArrayList();
    items.add("General Information");
    items.add("Specific Information");
    return items;
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }

}
