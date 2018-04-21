package com.example.faisal.preferred.fragment;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentHomeBinding;
import com.example.faisal.preferred.models.Deal;
import com.example.faisal.preferred.other.CustomFragment;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CustomFragment{
  FragmentHomeBinding dataBiding;
  DashboardActivity parent;
  private List<Deal> dealList = new ArrayList<>();

  public HomeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    dataBiding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(true);
    getAllDeals();
    dataBiding.menuButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.drawer.openDrawer(GravityCompat.START);
      }
    });
    return dataBiding.getRoot();
  }

  public void getAllDeals(){
    dealList.add(new Deal());
    dealList.add(new Deal());
    dealList.add(new Deal());
    initiateDealsList();
  }

  public void initiateDealsList(){
    DealsAdapter mAdapter = new DealsAdapter(dealList);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    SnapHelper snapHelper = new PagerSnapHelper();
    dataBiding.dealsList.setLayoutManager(mLayoutManager);
    snapHelper.attachToRecyclerView(dataBiding.dealsList);
    dataBiding.dealsList.setItemAnimator(new DefaultItemAnimator());
    dataBiding.dealsList.setAdapter(mAdapter);
  }

  @Override
  public void onBackButtonPressed() {

  }

  public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {

    private List<Deal> dealsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
      public View container;

      public MyViewHolder(View view) {
        super(view);
        container = view.findViewById(R.id.container);
      }
    }

    public DealsAdapter(List<Deal> moviesList) {
      this.dealsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.deal_item, parent, false);

      return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      Deal deal = dealsList.get(position);
      holder.container.setBackgroundResource(deal.featuredImage);
      holder.container.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          parent.setFragment(DashboardActivity.TAG_DEAL_CARD);
          parent.loadFragment();
        }
      });
    }

    @Override
    public int getItemCount() {
      return dealsList.size();
    }
  }

}
