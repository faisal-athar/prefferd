package com.example.faisal.preferred.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentWalletBinding;
import com.example.faisal.preferred.other.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment implements CustomFragment{
  DashboardActivity parent;
  FragmentWalletBinding dataBinding;

  public WalletFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(false);
    parent.setDrawerLockMode(false);
    dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_wallet, container, false);
    dataBinding.cashContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dataBinding.cashCheck.setVisibility(View.VISIBLE);
        dataBinding.simSimCheck.setVisibility(View.GONE);
      }
    });
    dataBinding.simSimContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dataBinding.cashCheck.setVisibility(View.GONE);
        dataBinding.simSimCheck.setVisibility(View.VISIBLE);
      }
    });
    return dataBinding.getRoot();
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }
}
