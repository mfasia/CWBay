package com.metafour.cwbay.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.User;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.remote.WebConnection;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;
import com.metafour.cwbay.util.Validation;

/**
 * Created by Noor on 1/22/2015.
 */
public class SignUpActivity extends ActionBarActivity implements WebConnection.Callback {

    private Context context;
    private Button createButton;
    private EditText fullName, userEmail, password, confPassword, phoneNumber;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        this.context = getApplicationContext();
        this.progressBar = (ProgressBar) findViewById(R.id.signUpProgressBar);
        this.fullName = (EditText)findViewById(R.id.signUpFullName);
        this.userEmail = (EditText)findViewById(R.id.signUpEmail);
        this.password = (EditText)findViewById(R.id.signUpPassword);
        this.confPassword = (EditText)findViewById(R.id.signUpConfPassword);
        this.createButton = (Button) findViewById(R.id.signUpCreate);
        this.phoneNumber = (EditText)findViewById(R.id.signUpPhone);
        checkViews();
    }

    @Override
    public void onResponse(WebConnection.Response response) {

    }

    private void createAccount(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Create account button clicked");
        User user = new User();
        user.setEmail(this.userEmail.getText().toString());
        user.setName(this.fullName.getText().toString());
        user.setPassword(this.password.getText().toString());
        user.setPhone(this.phoneNumber.getText().toString());

        Utility.showProgressBarAndDisableButton(progressBar, createButton);
        WebConnection.getInstance().request(this,"http://192.168.0.89:8000");
//        WebConnection.getInstance().connect(this, "http://192.168.0.89:8000");

        WebAPI.userCreate(context, new WebAPI.Callback<User>(){

            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Create account failed with reason : " + reason);
                Utility.showLongLengthToast(context, reason);
                Utility.hideProgressBarAndEnableButton(progressBar, createButton);
            }

            @Override
            public void onSuccess(User user) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Account creation successful. New user details is " + user.toString());
                Utility.hideProgressBarAndEnableButton(progressBar, createButton);
                openNextActivity(ProfileUpdateActivity.class);
            }
        }, user);
    }

    private void checkViews() {
        fullName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(fullName);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        userEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(userEmail, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isPhoneNumber(phoneNumber, false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        this.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( checkValidation () ) {
                    createAccount(v);
                } else {
                    Log.i(Constants.ACTIVITY_LOG_TAG, "Validation failed!");
                    Utility.showLongLengthToast(context, "Validation Failed!");
                }
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(fullName)) ret = false;
        if (!Validation.hasText(password)) ret = false;
        if (!Validation.isEmailAddress(userEmail, true)) ret = false;
        if (!Validation.isPhoneNumber(phoneNumber, false)) ret = false;

        return ret;
    }

    private void openNextActivity(Class clz) {
        startActivity(new Intent(this, clz));
        Utility.nextWithAnimation(this);
    }
}
