package com.example.faisal.preferred.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
  private int[] layouts;
  private TextView[] dots;
  ActivityWelcomeBinding binding;
  private WelcomeActivityPagerAdapter adapter;
  Typeface malGun;
  Typeface malGunBold;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
    // layouts of all welcome sliders
    // add few more layouts if you want
    layouts = new int[]{
      R.layout.slide1,
      R.layout.slide2,
      R.layout.slide3};

    // adding bottom dots
    addBottomDots(0);

    //applying fonts
    malGun = Typeface.createFromAsset(getAssets(),  "fonts/malgun.ttf");
    malGunBold = Typeface.createFromAsset(getAssets(),  "fonts/malgunbd.ttf");
    binding.activityWelcomeNextButton.setTypeface(malGunBold);

    adapter = new WelcomeActivityPagerAdapter();
    binding.activityWelcomeViewPager.setAdapter(adapter);
    binding.activityWelcomeViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    binding.activityWelcomeNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // checking for last page
        // if last page home screen will be launched
        int current = getItem(+1);
        if (current < layouts.length) {
          // move to next screen
          binding.activityWelcomeViewPager.setCurrentItem(current);
        } else {
          //Go to sign up
          startActivity(new Intent(WelcomeActivity.this , SignupActivity.class));
          finish();
        }
      }
    });
  }

  private void addBottomDots(int currentPage) {
    dots = new TextView[layouts.length];

    binding.layoutDots.removeAllViews();
    for (int i = 0; i < dots.length; i++) {
      dots[i] = new TextView(this);
      dots[i].setText(Html.fromHtml("&#8226;"));
      dots[i].setTextSize(35);
      dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
      binding.layoutDots.addView(dots[i]);
    }

    if (dots.length > 0)
      dots[currentPage].setTextColor(getResources().getColor(R.color.theme_primary_color));;
  }

  private int getItem(int i) {
    return binding.activityWelcomeViewPager.getCurrentItem() + i;
  }

  //  viewpager change listener
  ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

    @Override
    public void onPageSelected(int position) {
      addBottomDots(position);

      // changing the next button text
      if (position == layouts.length - 1) {
        binding.activityWelcomeNextButton.setText("Get Started");
      }
      else {
        binding.activityWelcomeNextButton.setText("Next");
      }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }
  };

  /**
   * View pager adapter
   */
  public class WelcomeActivityPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    public WelcomeActivityPagerAdapter() {
    }

    private void setFonts(View slide){
      TextView title = slide.findViewById(R.id.title);
      title.setTypeface(malGun);
      TextView desc = slide.findViewById(R.id.desc);
      desc.setTypeface(malGun);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      View view = layoutInflater.inflate(layouts[position], container, false);
      setFonts(view);
      container.addView(view);

      return view;
    }

    @Override
    public int getCount() {
      return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
      return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      View view = (View) object;
      container.removeView(view);
    }
  }
}
