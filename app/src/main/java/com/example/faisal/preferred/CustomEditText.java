package com.example.faisal.preferred;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by faisal on 2/25/18.
 */

public class CustomEditText {

  public void add(final EditText ... Inputs){
    for (int i=0 ; i< Inputs.length ; i++){
      final int index = i;
      Inputs[index].addTextChangedListener(new TextWatcher() {
        public void afterTextChanged(Editable s) {
          if (s.length() > 0)
            Inputs[index].setGravity(Gravity.BOTTOM);
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
      });
      Inputs[index].setOnFocusChangeListener(new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
          if (hasFocus)
            Inputs[index].setGravity(Gravity.BOTTOM);
          else{
            if (Inputs[index].getText().toString().length() == 0)
              Inputs[index].setGravity(Gravity.CENTER_VERTICAL);
          }
        }
      });
    }

  }

  public void addAutoComplete(final AutoCompleteTextView... Inputs){
    for (int i=0 ; i< Inputs.length ; i++){
      final int index = i;
      Inputs[index].addTextChangedListener(new TextWatcher() {
        public void afterTextChanged(Editable s) {
          if (s.length() > 0)
            Inputs[index].setGravity(Gravity.BOTTOM);
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
      });
      Inputs[index].setOnFocusChangeListener(new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
          if (hasFocus)
            Inputs[index].setGravity(Gravity.BOTTOM);
          else{
            if (Inputs[index].getText().toString().length() == 0)
              Inputs[index].setGravity(Gravity.CENTER_VERTICAL);
          }
        }
      });
    }

  }
}
