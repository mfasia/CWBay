package com.metafour.cwbay.remote;

import android.content.Context;
import android.util.Log;
import android.webkit.URLUtil;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.User;
import com.metafour.cwbay.model.UserBuilder;

import org.apache.http.client.utils.URLEncodedUtils;

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

    public static interface UserLoginCallback {
        public void onUserLoginFailed(String reason);
        public void onUserLoginSuccess(User user);
    }

    public static interface UserViewCallback {
        public void onUserViewFailed(String reason);
        public void onUserViewSuccess(User user);
    }

    public static interface UserEditCallback {
        public void onUserEditFailed(String reason);
        public void onUserEditSuccess(User user);
    }

    public static interface UserCreateCallback {
        public void onUserCreateFailed(String reason);
        public void onUserCreateSuccess(User user);
    }

    public static void userLogin(final Context context, final UserLoginCallback callback, final String email, final String pass) {
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
                            callback.onUserLoginFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.UNAUTHORIZED:
                            callback.onUserLoginFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.OK:
                            callback.onUserLoginSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onUserLoginFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_LOGIN_PATH + encodedEmail);
    }

    public static void userView(final Context context, final UserViewCallback callback, final String email) {
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
                            callback.onUserViewSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onUserViewFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_VIEW_PATH + encodedEmail);
    }

    public static void userEdit(final Context context, final UserEditCallback callback, final String email, final User user) {
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
                            callback.onUserEditSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onUserEditFailed(response.getContent());
                            break;
                    }

                }
            }
        },
        USER_EDIT_PATH + encodedEmail, UserBuilder.serialize(user));
    }

    public static void userCreate(final Context context, final UserCreateCallback callback, final User user) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.NOT_FOUND:
                            callback.onUserCreateFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.EXISTS:
                            callback.onUserCreateFailed("Alreay exist");
                            break;
                        case WebConnection.Status.UNAUTHORIZED:
                            callback.onUserCreateFailed(context.getResources().getString(R.string.login_invalid_cred));
                            break;
                        case WebConnection.Status.OK:
                            callback.onUserCreateSuccess(UserBuilder.build(response.getContent()));
                            break;
                        default:
                            callback.onUserCreateFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        USER_CREATE_PATH, UserBuilder.serialize(user));
    }
}
