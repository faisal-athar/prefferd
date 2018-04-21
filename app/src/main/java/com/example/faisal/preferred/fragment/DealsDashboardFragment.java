package com.example.faisal.preferred.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentDealsDashboardBinding;
import com.example.faisal.preferred.models.Deal;
import com.example.faisal.preferred.other.CustomFragment;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealsDashboardFragment extends Fragment implements CustomFragment{
  DashboardActivity parent;
  FragmentDealsDashboardBinding dataBinding;
  private List<Deal> dealList = new ArrayList<>();

  public DealsDashboardFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(false);
    dataBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_deals_dashboard, container, false);
    getAllDeals();
    return dataBinding.getRoot();
  }

  public void getAllDeals(){
    dealList.add(new Deal(R.drawable.skinny,"Shirt","Skinny Jeans","60% OFF","01:50:00"));
    dealList.add(new Deal(R.drawable.shirt,"Shirt","Men","50% OFF","02:01:30"));
    dealList.add(new Deal(R.drawable.shirt,"Shirt","Men","60% OFF","02:01:30"));
    initiateDealsList();
  }

  public void initiateDealsList(){
    DealsListAdapter mAdapter = new DealsListAdapter(dealList);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    dataBinding.dealsList.setLayoutManager(mLayoutManager);
    dataBinding.dealsList.setItemAnimator(new DefaultItemAnimator());
    dataBinding.dealsList.setAdapter(mAdapter);
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_HOME);
  }

  public class DealsListAdapter extends RecyclerView.Adapter<DealsListAdapter.mViewHolder> {

    private List<Deal> dealsList;

    public class mViewHolder extends RecyclerView.ViewHolder {
      public View container;

      public mViewHolder(View view) {
        super(view);
        container = view.findViewById(R.id.deal_list_item_container);
      }
    }

    public DealsListAdapter(List<Deal> dealsList) {
      this.dealsList = dealsList;
    }

    @Override
    public DealsListAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.deal_list_item, parent, false);

      return new mViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DealsListAdapter.mViewHolder holder, int position) {
      Deal deal = dealsList.get(position);
      holder.container.setBackgroundResource(R.drawable.deal_list_item_bg);
      ImageView thumb = holder.container.findViewById(R.id.thumbnail);
      thumb.setImageResource(deal.thumbnail);
    }

    @Override
    public int getItemCount() {
      return dealsList.size();
    }
  }

}
