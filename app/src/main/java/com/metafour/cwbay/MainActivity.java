package com.metafour.cwbay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.metafour.cwbay.remote.WebConnection;
import com.metafour.cwbay.ui.CategoryActivity;
import com.metafour.cwbay.ui.CategoryGridActivity;
import com.metafour.cwbay.ui.SignInActivity;
import com.metafour.cwbay.ui.SignUpActivity;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;


public class MainActivity extends ActionBarActivity implements WebConnection.Callback {

    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnCat;
    private Button btnCatG;
    private Button btnRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebConnection.getInstance().connect(this, "http://192.168.0.57:8000");
        btnSignIn = (Button)findViewById(R.id.mainBSignIn);
        btnSignUp = (Button)findViewById(R.id.mainBSignUp);
        btnCat = (Button)findViewById(R.id.mainBCat);
        btnCatG = (Button)findViewById(R.id.mainBCatG);
        btnRoot = (Button)findViewById(R.id.mainBRoot);

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
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryPage(v);
            }
        });
        btnCatG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryGridPage(v);
            }
        });
        btnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRootPage(v);
            }
        });
    }

    @Override
    public void onResponse(WebConnection.Response response) {
        Log.i(Constants.ACTIVITY_LOG_TAG, response.getStatus() + " " + response.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openSignInPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign in page");
        openNextActivity(SignInActivity.class);
    }


    public void openSignUpPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open sign up page");
        openNextActivity(SignUpActivity.class);
    }

    public void openCategoryPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category page");
        CategoryActivity.idToShow = 0;
        openNextActivity(CategoryActivity.class);
    }

    public void openCategoryGridPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open category grid page");
        CategoryGridActivity.idToShow = 0;
        openNextActivity(CategoryGridActivity.class);
    }

    public void openRootPage(View v) {
        Log.i(Constants.ACTIVITY_LOG_TAG, "Going to open root page");
        openNextActivity(RootActivity.class);
    }

    private void openNextActivity(Class clz) {
        startActivity(new Intent(this, clz));
        Utility.nextWithAnimation(this);
    }
}
