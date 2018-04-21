package com.example.faisal.preferred.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.faisal.preferred.R;

import java.util.ArrayList;

public class VerificationActivity1 extends AppCompatActivity {

  Spinner country_codes_spinner;
  EditText phone_number;
  Button submit;
  Button cancel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verification1);
    country_codes_spinner = findViewById(R.id.codes_spinner);
    submit = findViewById(R.id.submit);
    ArrayAdapter<String> codeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item
      , countryCodes());
    codeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    country_codes_spinner.setAdapter(codeAdapter);
    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(VerificationActivity1.this, VerificationActivity2.class));
        finish();
      }
    });
  }

  public ArrayList<String> countryCodes(){
    ArrayList<String> codes = new ArrayList<>();
    codes.add("+92");
    codes.add("+82");
    codes.add("+72");
    codes.add("+62");
    codes.add("+52");
    return codes;
  }
}
