package com.example.faisal.preferred.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentHistoryBinding;
import com.example.faisal.preferred.models.Deal;
import com.example.faisal.preferred.other.CustomFragment;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements CustomFragment{
  DashboardActivity parent;
  FragmentHistoryBinding dataBinding;
  private List<Deal> dealList = new ArrayList<>();

  public HistoryFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(false);
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false);
    getAllDeals();
    return dataBinding.getRoot();
  }

  public void getAllDeals(){
    dealList.add(new Deal(R.drawable.skinny,"Skinny Jeans","Men","60% OFF","01:50:00"));
    dealList.add(new Deal(R.drawable.shirt,"Shirt","Men","50% OFF","02:01:30"));
    dealList.add(new Deal(R.drawable.shirt,"Shirt","Men","60% OFF","02:01:30"));
    initiateDealsList();
  }

  public void initiateDealsList(){
    HistoryDealsListAdapter mAdapter = new HistoryDealsListAdapter(dealList);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    dataBinding.dealsListHistory.setLayoutManager(mLayoutManager);
    dataBinding.dealsListHistory.setItemAnimator(new DefaultItemAnimator());
    dataBinding.dealsListHistory.setAdapter(mAdapter);
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }

  private class HistoryDealsListAdapter extends RecyclerView.Adapter<HistoryDealsListAdapter.hViewHolder>{
    private List<Deal> dealsList;

    public class hViewHolder extends RecyclerView.ViewHolder {
      public View container;

      public hViewHolder(View view) {
        super(view);
        container = view.findViewById(R.id.deal_list_item_container);
      }
    }

    public HistoryDealsListAdapter(List<Deal> dealsList) {
      this.dealsList = dealsList;
    }

    @Override
    public hViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.deal_list_item, parent, false);

      return new HistoryDealsListAdapter.hViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(hViewHolder holder, int position) {
      Deal deal = dealsList.get(position);
      holder.container.setBackgroundResource(R.drawable.deal_list_item_bg);
      ImageView thumb = holder.container.findViewById(R.id.thumbnail);
      thumb.setImageResource(deal.thumbnail);

    }

    @Override
    public int getItemCount() {
      return this.dealsList.size();
    }
  }
}
