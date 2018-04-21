package com.example.faisal.preferred.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentDealCardBinding;
import com.example.faisal.preferred.other.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealCardFragment extends Fragment implements CustomFragment{

  FragmentDealCardBinding dataBinding;
  DashboardActivity parent;

  public DealCardFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_deal_card, container, false);
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(true);
    parent.setDrawerLockMode(true);
    dataBinding.backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackButtonPressed();
      }
    });
    dataBinding.claim.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.setFragment(DashboardActivity.TAG_DEAL_DETAIL);
      }
    });
    return dataBinding.getRoot();
  }

  @Override
  public void onDestroy() {
    parent.setDrawerLockMode(true);
    super.onDestroy();
  }

  @Override
  public void onBackButtonPressed() {
    parent.setDrawerLockMode(false);
    parent.setFragment(DashboardActivity.TAG_HOME);
  }
}
