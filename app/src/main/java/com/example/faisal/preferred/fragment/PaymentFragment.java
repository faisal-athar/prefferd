package com.example.faisal.preferred.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faisal.preferred.R;
import com.example.faisal.preferred.activity.DashboardActivity;
import com.example.faisal.preferred.databinding.FragmentPaymentBinding;
import com.example.faisal.preferred.models.Payment;
import com.example.faisal.preferred.other.CustomFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements CustomFragment{
  FragmentPaymentBinding dataBinding;
  DashboardActivity parent;
  public final static int WHITE = 0xFFFFFFFF;
  public final static int BLACK = 0xFF000000;


  public PaymentFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment, container, false);
    parent = (DashboardActivity) getActivity();
    parent.setFullScreenMode(false);
    parent.setDrawerLockMode(true);
    parent.hideHamburgerAndShowBackButton();
    setAdapter();
    initializeQRCode();
    dataBinding.buy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        parent.setFragment(DashboardActivity.TAG_THANK_YOU);
      }
    });
    return dataBinding.getRoot();
  }

  public void setAdapter(){
    PaymentAdapter paymentAdapter = new PaymentAdapter(parent, R.layout.payment_spinner_item
      , R.id.title, getPaymentMethods());
    dataBinding.payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    dataBinding.payment.setAdapter(paymentAdapter);
  }

  public void initializeQRCode(){
    try {
      int size = Resources.getSystem().getDisplayMetrics().widthPixels - 70;
      Bitmap bitmap = encodeAsBitmap(getJsonData(),size);
      dataBinding.qrCode.setImageBitmap(bitmap);
    }
    catch (Exception e){
      e.printStackTrace();
      Log.e("error",e.toString());
      Snackbar.make(parent.findViewById(android.R.id.content),"Unable to generate QR Code",Snackbar.LENGTH_LONG).show();
    }
  }

  public String getJsonData(){
    JSONObject obj = new JSONObject();
    try {
      obj.put("user_id" , 1);
      obj.put("deal_id" , 2);
    }
    catch (JSONException e){
      e.printStackTrace();
    }
    return obj.toString();
  }

  ArrayList getPaymentMethods(){
    ArrayList<Payment> payments = new ArrayList<>();
    payments.add(new Payment("Cash", R.drawable.paisa));
    payments.add(new Payment("SIM SIM", R.drawable.sim));
    return payments;
  }

  Bitmap encodeAsBitmap(String str , int size) throws WriterException {
    BitMatrix result;
    try {
      result = new MultiFormatWriter().encode(str,
        BarcodeFormat.QR_CODE, size, size, null);
    } catch (IllegalArgumentException iae) {
      // Unsupported format
      return null;
    }

    int w = result.getWidth();
    int h = result.getHeight();
    int[] pixels = new int[w * h];
    for (int y = 0; y < h; y++) {
      int offset = y * w;
      for (int x = 0; x < w; x++) {
        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
      }
    }

    Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
    return bitmap;
  }

  @Override
  public void onBackButtonPressed() {
    parent.setFragment(DashboardActivity.TAG_DEAL_DETAIL);
  }


  public class PaymentAdapter extends ArrayAdapter<Payment> {

    LayoutInflater inflater;
    ArrayList<Payment> payments;
    int resourceId;

    public PaymentAdapter(Activity context, int resourceId, int text_id, ArrayList<Payment> payments){

      super(context,resourceId,text_id, payments);
      inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      this.payments = payments;
      this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (convertView == null) {
        convertView = inflater.inflate(this.resourceId, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      Payment payment = this.payments.get(position);
      viewHolder.paymentTitle.setText(payment.getTitle());
      viewHolder.paymentIcon.setImageResource(payment.getIcon());
      return convertView;
    }

    @Override
    public int getCount() {
      return this.payments.size();
    }
  }

  static class ViewHolder {
    TextView paymentTitle;
    ImageView paymentIcon;

    public ViewHolder(View view) {
      paymentTitle = view.findViewById(R.id.title);
      paymentIcon  = view.findViewById(R.id.icon);
    }
  }

}
