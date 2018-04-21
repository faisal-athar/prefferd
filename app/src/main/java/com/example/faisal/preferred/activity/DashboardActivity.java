package com.example.faisal.preferred.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.fragment.DealCardFragment;
import com.example.faisal.preferred.fragment.DealDetailFragment;
import com.example.faisal.preferred.fragment.DealsDashboardFragment;
import com.example.faisal.preferred.fragment.HistoryFragment;
import com.example.faisal.preferred.fragment.HomeFragment;
import com.example.faisal.preferred.fragment.PaymentFragment;
import com.example.faisal.preferred.fragment.PreferencesFragment;
import com.example.faisal.preferred.fragment.ProfileFragment;
import com.example.faisal.preferred.fragment.SupportFragment;
import com.example.faisal.preferred.fragment.ThankYouFragment;
import com.example.faisal.preferred.fragment.WalletFragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements PreferencesFragment.OnFragmentInteractionListener {

  public NavigationView navigationView;
  public DrawerLayout drawer;
  public View navHeader;
  public Toolbar toolbar;
  public TextView txtName, txtEmail;
  public ImageView imgProfile;
  public ListView navListView;

  public Handler mHandler;

  // tags used to attach the fragments
  public static final String TAG_PREFERENCES = "PREFERENCES";
  public static final String TAG_HOME = "HOME";
  public static final String TAG_DEALS_DASHBOARD = "DEAL DASHBOARD";
  public static final String TAG_DEAL_CARD = "DEAL CARD";
  public static final String TAG_DEAL_DETAIL = "DEAL DETAIL";
  public static final String TAG_PAYMENT = "QR code";
  public static final String TAG_THANK_YOU = "Thankx";
  public static final String TAG_PROFILE = "EDIT PROFILE";
  public static final String TAG_WALLET = "WALLET";
  public static final String TAG_HISTORY = "HISTORY";
  public static final String TAG_EARN_MONEY = "EARN MONEY";
  public static final String TAG_SUPPORT = "SUPPORT";
  public static final String TAG_LOG_OUT = "LOG OUT";
  public static String CURRENT_TAG = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    toolbar =  findViewById(R.id.toolbar);
    toolbar.setTitleTextAppearance(this, R.style.customfontstyle);
    setSupportActionBar(toolbar);
    mHandler = new Handler();
    drawer =  findViewById(R.id.drawer_layout);
    navigationView =  findViewById(R.id.nav_view);
    navHeader = findViewById(R.id.navheader);
    txtName =  navHeader.findViewById(R.id.name);
    txtEmail =  navHeader.findViewById(R.id.email);
    imgProfile =  navHeader.findViewById(R.id.img_profile);
    ConstraintLayout editProfile = navHeader.findViewById(R.id.editProfile);
    editProfile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setFragment(TAG_PROFILE);
      }
    });


    // load nav menu header data
    loadNavHeader();

    //load nav menu
    loadNavMenu();

    // initializing navigation menu
    setUpNavigationView();

    if (savedInstanceState == null) {
      if ( isFirstTimeDashboardLanding()){
        CURRENT_TAG = TAG_PREFERENCES;
      }
      else{
        CURRENT_TAG = TAG_HOME;
      }

      loadFragment();
    }

  }

  public void loadNavMenu(){
    navListView = findViewById(R.id.itemListView);
    List<String> items = new ArrayList<>();
    items.add(TAG_HOME);
    items.add(TAG_DEALS_DASHBOARD);
    items.add(TAG_PREFERENCES);
    items.add(TAG_WALLET);
    items.add(TAG_HISTORY);
    items.add(TAG_EARN_MONEY);
    items.add(TAG_SUPPORT);
    items.add(TAG_LOG_OUT);
    ListAdapter customAdapter = new ListAdapter(this, R.layout.nav_item, items);
    navListView .setAdapter(customAdapter);
  }

  /***
   * Load navigation menu header information
   * like background image, profile image
   * name, email
   */
  public void loadNavHeader() {
    // name, website
    //txtName.setText("Ravi Tamada");
    //txtEmail.setText("www.androidhive.info");

    // Loading profile image
//    Glide.with(this).load("Enter profile url here")
//      .crossFade()
//      .thumbnail(0.5f)
//      .bitmapTransform(new CircleTransform(this))
//      .diskCacheStrategy(DiskCacheStrategy.ALL)
//      .into(imgProfile);

  }

  /***
   * Returns respected fragment that user
   * selected from navigation menu
   */
  public void loadFragment() {

    if(!CURRENT_TAG.equals(TAG_DEAL_CARD) && !CURRENT_TAG.equals(TAG_DEAL_DETAIL) && !CURRENT_TAG.equals(TAG_HOME))
      setToolbarTitle();

    // if user select the current navigation menu again, don't do anything
    // just close the navigation drawer
    if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
      drawer.closeDrawers();
      return;
    }

    // Sometimes, when fragment has huge data, screen seems hanging
    // when switching between navigation menus
    // So using runnable, the fragment is loaded with cross fade effect
    // This effect can be seen in GMail app
    Runnable mPendingRunnable = new Runnable() {
      @Override
      public void run() {
        // update the main content by replacing fragments
        Fragment fragment = getFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
          android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
        ((ArrayAdapter) navListView.getAdapter()).notifyDataSetChanged();
      }
    };

    // If mPendingRunnable is not null, then add to the message queue
    if (mPendingRunnable != null) {
      mHandler.post(mPendingRunnable);
    }

    //Closing drawer on item click
    drawer.closeDrawers();
  }

  public boolean isFirstTimeDashboardLanding(){
    return true;
  }

  public void setFullScreenMode(boolean flag){
    if (flag){
      getSupportActionBar().hide();
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    else {
      getSupportActionBar().show();
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
  }

  public void setFragment(String TAG){
    CURRENT_TAG = TAG;
    loadFragment();
  }

  public Fragment getFragment() {
    switch (CURRENT_TAG) {
      case TAG_PREFERENCES:
        return new PreferencesFragment();
      case TAG_HOME:
        return new HomeFragment();
      case TAG_DEAL_CARD:
        return new DealCardFragment();
      case TAG_DEAL_DETAIL:
        return new DealDetailFragment();
      case TAG_PAYMENT:
        return new PaymentFragment();
      case TAG_THANK_YOU:
        return new ThankYouFragment();
      case TAG_DEALS_DASHBOARD:
        return new DealsDashboardFragment();
      case TAG_WALLET:
        return new WalletFragment();
      case TAG_PROFILE:
        return new ProfileFragment();
      case TAG_HISTORY:
        return new HistoryFragment();
      case TAG_SUPPORT:
        return new SupportFragment();
      case TAG_LOG_OUT:
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        finish();
      default:
        Snackbar.make(this.findViewById(android.R.id.content),CURRENT_TAG,Snackbar.LENGTH_LONG).show();
        return new PreferencesFragment();
    }
  }

  public void setDrawerLockMode(boolean lock){
    if (lock)
      drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    else
      drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
  }

  public void hideHamburgerAndShowBackButton(){
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (CURRENT_TAG){
          case TAG_PAYMENT:
            PaymentFragment paymentFragment = (PaymentFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            paymentFragment.onBackButtonPressed();
            break;
        }
      }
    });
  }

  public void hideBackButtonAndShowHamburger(){
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    getSupportActionBar().setDisplayShowHomeEnabled(false);
    setUpNavigationView();
  }

  public void setToolbarTitle() {
    if(CURRENT_TAG.equals(TAG_PAYMENT))
      getSupportActionBar().setTitle(CURRENT_TAG.substring(0, 2).toUpperCase() + CURRENT_TAG.substring(2).toLowerCase());
    else if(!CURRENT_TAG.equals(TAG_THANK_YOU))
      getSupportActionBar().setTitle(CURRENT_TAG.substring(0, 1).toUpperCase() + CURRENT_TAG.substring(1).toLowerCase());
  }


  public void setUpNavigationView() {
    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

      @Override
      public void onDrawerClosed(View drawerView) {
        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
        super.onDrawerClosed(drawerView);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
        super.onDrawerOpened(drawerView);
      }
    };

    //Setting the actionbarToggle to drawer layout
    drawer.setDrawerListener(actionBarDrawerToggle);

    //calling sync state is necessary or else your hamburger icon wont show up
    actionBarDrawerToggle.syncState();
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawers();
      return;
    }

    switch (CURRENT_TAG){
      case TAG_DEAL_DETAIL:
        DealDetailFragment dealDetailFragment = (DealDetailFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        dealDetailFragment.onBackButtonPressed();
        break;
      case TAG_DEAL_CARD:
        DealCardFragment dealCardFragment = (DealCardFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        dealCardFragment.onBackButtonPressed();
        break;
      case TAG_PREFERENCES:
        PreferencesFragment preferencesFragment = (PreferencesFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        preferencesFragment.onBackButtonPressed();
        break;
      case TAG_PAYMENT:
        PaymentFragment paymentFragment = (PaymentFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        paymentFragment.onBackButtonPressed();
        break;
      case TAG_THANK_YOU:
        ThankYouFragment thankYouFragment = (ThankYouFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        thankYouFragment.onBackButtonPressed();
        break;
      case TAG_DEALS_DASHBOARD:
        DealsDashboardFragment dealsDashboardFragment = (DealsDashboardFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        dealsDashboardFragment.onBackButtonPressed();
        break;
      case TAG_WALLET:
        WalletFragment walletFragment = (WalletFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        walletFragment.onBackButtonPressed();
        break;
      case TAG_PROFILE:
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        profileFragment.onBackButtonPressed();
        break;
      case TAG_HISTORY:
        HistoryFragment historyFragment = (HistoryFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        historyFragment.onBackButtonPressed();
        break;
      case TAG_SUPPORT:
        SupportFragment supportFragment = (SupportFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
        supportFragment.onBackButtonPressed();
        break;
      default:
        super.onBackPressed();

    }
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }

  public void onMenuItemSelected(String selectedTag){
    CURRENT_TAG = selectedTag;
    loadFragment();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:

        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public class ListAdapter extends ArrayAdapter<String> {
    int items_length = 0;
    public ListAdapter(Context context, int textViewResourceId) {
      super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<String> items) {
      super(context, resource, items);
      items_length = items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      View v = convertView;

      if (v == null) {
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.nav_item, null);
      }
      TextView item = v.findViewById(R.id.menu_item);

      if (getItem(position).equals(CURRENT_TAG))
        item.setTextColor(getResources().getColor(R.color.theme_primary_color));
      else
        item.setTextColor(getResources().getColor(R.color.text_grey));

      if (items_length-1 == position){
        View divider = v.findViewById(R.id.menu_item_divider);
        divider.setVisibility(View.GONE);
      }

      final String tag = getItem(position);
      item.setText(tag);

      item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onMenuItemSelected(tag);
        }
      });
      return v;
    }

  }
}
