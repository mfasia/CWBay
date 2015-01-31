package com.metafour.cwbay.ui;


import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class ProfileUpdateActivity extends ActionBarActivity implements WebConnection.Callback {

    private Context context;
    private Button updateButton,changePassword;
    private EditText fullName, userEmail, phoneNumber,location;
    private ProgressBar progressBar;
    User user = new User();
    User loggedUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);
        this.context = getApplicationContext();
        this.progressBar = (ProgressBar) findViewById(R.id.profileUpdateProgressBar);
        this.fullName = (EditText)findViewById(R.id.profileUpdateFullName);
        this.userEmail = (EditText)findViewById(R.id.profileUpdateEmail);
        this.updateButton = (Button) findViewById(R.id.profileUpdate);
        this.phoneNumber = (EditText)findViewById(R.id.profileUpdatePhone);
        this.changePassword = (Button) findViewById(R.id.profileUpdateChangePassword);
        this.location = (EditText)findViewById(R.id.profileUpdateLocation);
        this.loggedUser = WebAPI.getLoggedInUser(ProfileUpdateActivity.this);
        setUserData();
        changePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showChangePasswordView();

            }
        });
        checkViews();
    }

    @Override
    public void onResponse(WebConnection.Response response) {

    }

    private void setUserData(){
        if(loggedUser != null){
            Log.i(Constants.ACTIVITY_LOG_TAG,"Logged user - "+ loggedUser.toString());
            fullName.setText(loggedUser.getName());
            userEmail.setText(loggedUser.getEmail());
            phoneNumber.setText(loggedUser.getPhone());
        } else {
            Log.i(Constants.ACTIVITY_LOG_TAG,"No logged user found!");
        }
    }

    public void showChangePasswordView(){
        final Dialog dialog = new Dialog(ProfileUpdateActivity.this);
        dialog.setContentView(R.layout.change_password);
        dialog.setTitle("Change Password");

        EditText password = (EditText) dialog.findViewById(R.id.dialog_txt_password);
        EditText confPassword = (EditText) dialog.findViewById(R.id.dialog_txt_password);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText oldPassword = (EditText) dialog.findViewById(R.id.dialog_txt_old_password);
                if(oldPassword.getText().toString().isEmpty() || (!oldPassword.getText().toString().isEmpty() && oldPassword.getText().toString().equalsIgnoreCase(loggedUser.getPassword()))){
                    Log.i(Constants.ACTIVITY_LOG_TAG,"No old password!");
                    dialog.dismiss();
                }
                EditText password = (EditText) dialog.findViewById(R.id.dialog_txt_password);
                EditText confPassword = (EditText) dialog.findViewById(R.id.dialog_txt_password);
                if(password.getText().toString() != "" && (password.getText().toString().matches(confPassword.toString()))){
                    user.setPassword(password.getText().toString());
                    dialog.dismiss();
                }else{
                    Log.i(Constants.ACTIVITY_LOG_TAG,"Doesn't match!");
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constants.ACTIVITY_LOG_TAG,"Cancelled password change!");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateAccount(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Create account button clicked");
        user.setEmail(this.userEmail.getText().toString());
        user.setName(this.fullName.getText().toString());
        user.setPhone(this.phoneNumber.getText().toString());
        user.setPlace(this.location.getText().toString());

        Utility.showProgressBarAndDisableButton(progressBar, updateButton);
        WebConnection.getInstance().request(this, "http://192.168.0.89:8000");

        WebAPI.userEdit(context, new WebAPI.Callback<User>() {

            @Override
            public void onFailed(String reason) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Update account failed with reason : " + reason);
                Utility.showLongLengthToast(context, reason);
                Utility.hideProgressBarAndEnableButton(progressBar, updateButton);
            }

            @Override
            public void onSuccess(User user) {
                Log.i(Constants.ACTIVITY_LOG_TAG, "Update account successful.user details is " + user.toString());
                Utility.hideProgressBarAndEnableButton(progressBar, updateButton);
            }
        }, this.userEmail.getText().toString(), user);
    }

    private void checkViews() {
        fullName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(fullName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        this.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( checkValidation () ) {
                    updateAccount(v);
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
        if (!Validation.isEmailAddress(userEmail, true)) ret = false;
        if (!Validation.isPhoneNumber(phoneNumber, false)) ret = false;

        return ret;
    }

}
