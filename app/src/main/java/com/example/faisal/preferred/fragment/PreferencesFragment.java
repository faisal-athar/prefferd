package com.example.faisal.preferred.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentPreferencesBinding;
import com.example.faisal.preferred.other.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferencesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PreferencesFragment extends Fragment implements CustomFragment{

  private OnFragmentInteractionListener mListener;
  private FragmentPreferencesBinding dataBiding;
  public DashboardActivity parent;

  public PreferencesFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    dataBiding = DataBindingUtil.inflate(inflater, R.layout.fragment_preferences, container, false);
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(false);
    setUpSeekBar();
    dataBiding.submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.setFragment(DashboardActivity.TAG_HOME);
      }
    });
    return dataBiding.getRoot();
  }

  public void setUpSeekBar(){
    dataBiding.seekBar.setMax(25);
    dataBiding.seekBar.setProgress(1);

    // perform seek bar change listener event used for getting the progress value
    dataBiding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String rad = Integer.toString(progress) + "KM";
        dataBiding.currentRadius.setText(rad);
      }

      public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
      }

      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onBackButtonPressed() {
    if (parent.isFirstTimeDashboardLanding())
      parent.finish();
    else
      parent.setFragment(DashboardActivity.TAG_HOME);
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
