package com.metafour.cwbay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.metafour.cwbay.model.User;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.remote.WebConnection;
import com.metafour.cwbay.util.AppUtility;

/**
 * Created by Noor on 1/22/2015.
 */
public class SignUpActivity extends ActionBarActivity implements WebConnection.Callback {

    private Context context;
    private Button createButton;
    private EditText fullName, userEmail, password, confPassword;
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

        this.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(v);
            }
        });
    }

    @Override
    public void onResponse(WebConnection.Response response) {

    }

    private void createAccount(View v) {
        Log.i(AppUtility.LOG_TAG, "Create account button clicked");
        Log.i(AppUtility.LOG_TAG, "Create account button clicked");
        User user = new User();
        user.setEmail(this.userEmail.getText().toString());
        user.setName(this.fullName.getText().toString());
        user.setPassword(this.password.getText().toString());

        AppUtility.showProgressBarAndDisableButton(progressBar, createButton);
        WebConnection.getInstance().connect(this, "http://192.168.0.57:8000");

        WebAPI.userCreate(context, new WebAPI.UserCreateCallback(){

            @Override
            public void onUserCreateFailed(String reason) {
                Log.i(AppUtility.LOG_TAG, "Create account failed with reason : " + reason);
                AppUtility.showLongLengthToast(context, reason);
                AppUtility.hideProgressBarAndEnableButton(progressBar, createButton);
            }

            @Override
            public void onUserCreateSuccess(User user) {
                Log.i(AppUtility.LOG_TAG, "Account creation successful. New user details is " + user.toString());
                AppUtility.hideProgressBarAndEnableButton(progressBar, createButton);
            }
        }, user);
    }
}
