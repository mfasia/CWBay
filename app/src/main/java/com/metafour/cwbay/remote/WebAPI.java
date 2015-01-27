package com.metafour.cwbay.remote;

import android.content.Context;
import android.util.Log;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.CategoryBuilder;
import com.metafour.cwbay.model.User;
import com.metafour.cwbay.model.UserBuilder;
import com.metafour.cwbay.util.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by nadim on 1/21/15.
 */
public class WebAPI {
    private static final String URL_ENCODING = "UTF-8";

    private static final String USER_LOGIN_PATH = "/user/login/";
    private static final String USER_VIEW_PATH = "/user/view/";
    private static final String USER_EDIT_PATH = "/user/edit/";
    private static final String USER_CREATE_PATH = "/user/create";

    private static final String CATEGORY_VIEW_PATH = "/category/view/";

    public static interface Callback<T> {
        public void onFailed(String reason);
        public void onSuccess(T responseObject);
    }

    /**
     * Web API method to login using user email and password.
     * @param context App context
     * @param callback User login callback
     * @param email User email
     * @param pass User password
     */
    public static void userLogin(final Context context, final Callback<User> callback, final String email, final String pass) {
        WebConnection.getInstance().setCredentials(email, pass);
        String encodedEmail = email;
        try {
            URLEncoder.encode(encodedEmail, URL_ENCODING);
        } catch (UnsupportedEncodingException e) {}

        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.NOT_FOUND:
                            callback.onFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.UNAUTHORIZED:
                            callback.onFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.OK:
                            callback.onSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_LOGIN_PATH + encodedEmail);
    }

    public static void userView(final Context context, final Callback<User> callback, final String email) {
        String encodedEmail = email;
        try {
            URLEncoder.encode(encodedEmail, URL_ENCODING);
        } catch (UnsupportedEncodingException e) {}

        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            callback.onSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_VIEW_PATH + encodedEmail);
    }

    public static void userEdit(final Context context, final Callback<User> callback, final String email, final User user) {
        String encodedEmail = email;
        try {
            URLEncoder.encode(encodedEmail, URL_ENCODING);
        } catch (UnsupportedEncodingException e) {}

        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            callback.onSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onFailed(response.getContent());
                            break;
                    }

                }
            }
        },
        USER_EDIT_PATH + encodedEmail, UserBuilder.serialize(user));
    }

    public static void userCreate(final Context context, final Callback<User> callback, final User user) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    Log.i(Constants.ACTIVITY_LOG_TAG, response.getStatus() + "");
                    switch (response.getStatus()) {
                        case WebConnection.Status.EXISTS:
                            callback.onFailed("Alreay exist");
                            break;
                        case WebConnection.Status.OK:
                            callback.onSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_CREATE_PATH, UserBuilder.serialize(user));
    }


    public static void categoryView(final Context context, final Callback<Category> callback, final int id) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            callback.onSuccess(CategoryBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        CATEGORY_VIEW_PATH + id);
    }
}
