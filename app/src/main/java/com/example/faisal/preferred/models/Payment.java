package com.example.faisal.preferred.models;

/**
 * Created by faisal on 3/14/18.
 */

public class Payment {
  public String title;
  public int icon;

  public Payment(String title, int icon){
    this.title = title;
    this.icon = icon;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getIcon() {
    return icon;
  }

  public void setIcon(int icon) {
    this.icon = icon;
  }
}
