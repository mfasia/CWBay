package com.metafour.cwbay;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * Created by md mamun on 30-Jan-15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnCat;
    private Button btnCatG;
    private Button btnRoot;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        btnSignIn = (Button) activity.findViewById(R.id.mainBSignIn);
        btnSignUp = (Button)activity.findViewById(R.id.mainBSignUp);
        btnCat = (Button)activity.findViewById(R.id.mainBCat);
        btnCatG = (Button)activity.findViewById(R.id.mainBCatG);

    }

    @SmallTest
    public void testOpenSignInBtn() {
      assertEquals("Sign in", btnSignIn.getText().toString());
       TouchUtils.clickView(this, btnSignIn);

    }

    /*@SmallTest
    public void testSignUpBtn() {
        assertEquals("Create account", btnSignUp.getText().toString());
        TouchUtils.clickView(this, btnSignUp);
    }*/
}
