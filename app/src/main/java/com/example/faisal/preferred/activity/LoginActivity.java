package com.example.faisal.preferred.activity;

import com.example.faisal.preferred.CustomEditText;
import com.example.faisal.preferred.CustomLoader;
import com.example.faisal.preferred.R;
import com.facebook.login.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

  /**
   * Id to identity READ_CONTACTS permission request.
   */
  private static final int REQUEST_READ_CONTACTS = 0;

  /**
   * A dummy authentication store containing known user names and passwords.
   * TODO: remove after connecting to a real authentication system.
   */
  private static final String[] DUMMY_CREDENTIALS = new String[]{
    "foo@example.com:hello", "bar@example.com:world"
  };
  /**
   * Keep track of the login task to ensure we can cancel it if requested.
   */
  private UserLoginTask mAuthTask = null;

  // UI references.
  private AutoCompleteTextView mEmailView;
  private EditText mPasswordView;
  public View mProgressView;
  public CustomLoader mloader;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    CustomEditText customEditText = new CustomEditText();

    // Set up the login form.
    mEmailView = findViewById(R.id.email);

    mPasswordView = findViewById(R.id.password);
    mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_FLAG_NAVIGATE_NEXT){
          mPasswordView.requestFocus();
          return true;
        }
        return false;
      }
    });

    Button mEmailSignInButton =  findViewById(R.id.email_sign_in_button);
    TextView signUpButton = findViewById(R.id.sign_up_button);
    mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        attemptLogin();
      }
    });
    signUpButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        finish();
      }
    });

    customEditText.add(mPasswordView);
    customEditText.addAutoComplete(mEmailView);

    mProgressView = findViewById(R.id.mProgressView);
    mloader = new CustomLoader(mProgressView, this);
  }


  /**
   * Attempts to sign in or register the account specified by the login form.
   * If there are form errors (invalid email, missing fields, etc.), the
   * errors are presented and no actual login attempt is made.
   */
  private void attemptLogin() {
    if (mAuthTask != null) {
      return;
    }

    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);

    // Store values at the time of the login attempt.
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();

    boolean cancel = false;

    // Check for a valid password, if the user entered one.
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      cancel = true;
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
      mEmailView.setError(getString(R.string.error_field_required));
      cancel = true;
    }

    if (!cancel) {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.

      mAuthTask = new UserLoginTask(email, password);
      mAuthTask.execute((Void) null);
    }
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 4;
  }


  /**
   * Represents an asynchronous login/registration task used to authenticate
   * the user.
   */
  public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    //private final Context context;

    UserLoginTask(String email, String password) {
      mEmail = email;
      mPassword = password;
      //this.context = context;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mloader.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
      // TODO: attempt authentication against a network service.

      try {
        // Simulate network access.
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        return false;
      }

      for (String credential : DUMMY_CREDENTIALS) {
        String[] pieces = credential.split(":");
        if (pieces[0].equals(mEmail)) {
          // Account exists, return true if the password matches.
          return pieces[1].equals(mPassword);
        }
      }

      // TODO: register the new account here.
      return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
      mAuthTask = null;
      mloader.hide();

      if (true) {
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();
      } else {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        //mPasswordView.requestFocus();
      }
    }

    @Override
    protected void onCancelled() {
      mAuthTask = null;
      mloader.hide();
    }
  }
}

