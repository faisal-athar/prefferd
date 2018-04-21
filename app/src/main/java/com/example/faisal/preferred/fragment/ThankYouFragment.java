package com.example.faisal.preferred.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentThankyouBinding;
import com.example.faisal.preferred.other.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThankYouFragment extends Fragment implements CustomFragment{
  DashboardActivity parent;
  FragmentThankyouBinding dataBinding;
  public ThankYouFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.hideBackButtonAndShowHamburger();
    parent.setDrawerLockMode(false);
    dataBinding=  DataBindingUtil.inflate(inflater,R.layout.fragment_thankyou, container, false);
    dataBinding.submitRatings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("Ratings " , dataBinding.ratings.getRating() + "");
      }
    });
    dataBinding.share.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Intent i = new Intent(Intent.ACTION_SEND);
          i.setType("text/plain");
          i.putExtra(Intent.EXTRA_SUBJECT, "Preferred Deal");
          String sAux = "\nHey , I just bought this deal and its awesome. Checkout this link  \n\n";
          sAux = sAux + "https://im.preferred.com/zshanjavaid \n\n";
          i.putExtra(Intent.EXTRA_TEXT, sAux);
          startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
          //e.toString();
        }
      }
    });
    dataBinding.copy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) parent.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Preferred Deal", "https://im.preferred.com/zshanjavaid");
        clipboard.setPrimaryClip(clip);
      }
    });
    dataBinding.viewMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.setFragment(DashboardActivity.TAG_HOME);
      }
    });
    return dataBinding.getRoot();
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }

}
