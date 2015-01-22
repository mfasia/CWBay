package com.metafour.cwbay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.metafour.cwbay.model.User;
import com.metafour.cwbay.remote.WebAPI;
import com.metafour.cwbay.remote.WebConnection;


public class MainActivity extends ActionBarActivity implements WebConnection.Callback {

    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnSignIn = (Button)findViewById(R.id.mainBSignIn);
        btnSignUp = (Button)findViewById(R.id.mainBSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage(v);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage(v);
            }
        });
    }

    @Override
    public void onResponse(WebConnection.Response response) {
        Log.i("CWBay", response.getStatus() + " " + response.getContent());
    }

    public void openSignInPage(View v) {
        Log.i("CWBay", "Going to open sign in page");
        startActivity(new Intent(this, SignInActivity.class));
    }


    public void openSignUpPage(View v) {
        Log.i("CWBay", "Going to open sign up page");
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
