package com.example.faisal.preferred.models;

import com.example.faisal.preferred.R;

/**
 * Created by faisal on 3/9/18.
 */

public class Deal {

  public int featuredImage = R.drawable.dummy_deal;
  public int thumbnail;
  public String title;
  public String category;
  public String offer;
  public String time;
  public Deal(){
  }

  public Deal(int thumbnail , String title , String category  , String offer , String time){
    this.thumbnail = thumbnail;
    this.title = title;
    this.category = category;
    this.offer = offer;
    this.time = time;
  }
}

