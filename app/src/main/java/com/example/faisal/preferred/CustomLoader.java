package com.example.faisal.preferred;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by faisal on 2/28/18.
 */

public class CustomLoader {

  public View progressView;
  public AVLoadingIndicatorView loader;
  public Context context;
  public TimeOut timer = null;

  public CustomLoader(View view , Context context){
    loader = view.findViewById(R.id.progress_circle);
    progressView = view;
    this.context = context;
  }


  public void show(){
    progressView.setVisibility(View.VISIBLE);
    progressView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    loader.smoothToShow();
  }

  public void hide(){
    progressView.clearAnimation();
    progressView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out));
    timer = new TimeOut();
    timer.execute((Void) null);
  }

  public class TimeOut extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {
      try {
        // Simulate network access.
        Thread.sleep(700);
      } catch (InterruptedException e) {
        return false;
      }
      return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
      super.onPostExecute(aBoolean);
      progressView.setVisibility(View.GONE);
      progressView.clearAnimation();
    }
  }
}
