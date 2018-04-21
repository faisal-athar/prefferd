package com.example.faisal.preferred.fragment;


import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentDealDetailBinding;
import com.example.faisal.preferred.other.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealDetailFragment extends Fragment implements CustomFragment{

  FragmentDealDetailBinding dataBinding;
  DashboardActivity parent;

  public DealDetailFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_deal_detail, container, false);
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(true);
    dataBinding.dealDetailContainer.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels;
    dataBinding.backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackButtonPressed();
      }
    });
    dataBinding.paymentMethod.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.setFragment(DashboardActivity.TAG_PAYMENT);
      }
    });
    return dataBinding.getRoot();
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_DEAL_CARD);
  }
}
