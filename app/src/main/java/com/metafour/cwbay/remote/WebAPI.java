package com.metafour.cwbay.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.metafour.cwbay.R;
import com.metafour.cwbay.model.Ad;
import com.metafour.cwbay.model.AdBuilder;
import com.metafour.cwbay.model.Category;
import com.metafour.cwbay.model.CategoryBuilder;
import com.metafour.cwbay.model.Subcategory;
import com.metafour.cwbay.model.SubcategoryBuilder;
import com.metafour.cwbay.model.User;
import com.metafour.cwbay.model.UserBuilder;
import com.metafour.cwbay.util.Constants;
import com.metafour.cwbay.util.Utility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nadim on 1/21/15.
 */
public class WebAPI {
    private static final String URL_ENCODING = "UTF-8";

    private static final Map<String, String> mockUserIds = new HashMap<String, String>(){{
        put("nadim@meta4.com123", "54cb50fb96d6b28406431f11");
        put("noor@meta4.com123", "54cb514f96d6b28406431f13");
        put("mazhar@meta4.com123", "54cb517c96d6b28a06431f14");
        put("murad@meta4.com123", "54cb51ad96d6b29706431f15");
    }};

    private static final String[] mockAdIds = { "54cba1e196d6b2930f431f79", "54cba20a96d6b29d0f431f7c", "54cba22696d6b29a0f431f83" };

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
        String mockEmail = mockUserIds.get(email + pass);
        if (mockEmail == null) mockEmail = "54cb43c396d6b20805431f06";

        String encodedEmail = mockEmail;
        try {
            encodedEmail = URLEncoder.encode(encodedEmail, URL_ENCODING);
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
                            User user = UserBuilder.build(response.getContent());
                            setLoggedInUser(context, user);
                            callback.onSuccess(user);
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        encodedEmail);
    }

    public static void userLogout(final Context context, final Callback<User> callback) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    setLoggedInUser(context, null);
                    callback.onSuccess(null);
                }
            }
        });
    }

    public static void userEdit(final Context context, final Callback<User> callback, final String email, final User user) {
        String mockUri = mockUserIds.get(user.getEmail() + "123");
        if (email.equalsIgnoreCase(user.getEmail())) mockUri = "54cb552696d6b2ef06431f1a";
        else {
            if (mockUri == null) mockUri = "54cb552696d6b2ef06431f1a";
            else mockUri = "54cb53d696d6b2d206431f17";
        }

        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            callback.onSuccess(UserBuilder.build(response.getContent()));
                            break;
                        case WebConnection.Status.EXISTS:
                            callback.onFailed(context.getResources().getString(R.string.email_is_not_available));
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }

                }
            }
        },
        mockUri, UserBuilder.serialize(user));
    }

    public static void userCreate(final Context context, final Callback<User> callback, final User user) {
        String mockUri = mockUserIds.get(user.getEmail() + "123");
        if (mockUri == null) mockUri = "54cb552696d6b2ef06431f1a";
        else mockUri = "54cb53d696d6b2d206431f17";

        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    Log.i(Constants.ACTIVITY_LOG_TAG, response.getStatus() + "");
                    switch (response.getStatus()) {
                        case WebConnection.Status.EXISTS:
                            callback.onFailed(context.getResources().getString(R.string.email_is_not_available));
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
        mockUri, UserBuilder.serialize(user));
    }

    public static User getLoggedInUser(Context context) {
        SharedPreferences pref = Utility.getPref(context);
        String email = pref.getString("user.email", null);
        if (email == null) return null;
        User user = new User();
        user.setEmail(email);
        user.setName(pref.getString("user.name", null));
        user.setPhone(pref.getString("user.phone", null));
        user.setPlace(pref.getString("user.place", null));
        return user;
    }

    private static void setLoggedInUser(Context context, User user) {
        SharedPreferences pref = Utility.getPref(context);
        SharedPreferences.Editor editor = pref.edit();

        if (user == null) {
            editor.putString("user.name", null);
            editor.putString("user.email", null);
            editor.putString("user.phone", null);
            editor.putString("user.place", null);
        } else {
            editor.putString("user.name", user.getName());
            editor.putString("user.email", user.getEmail());
            editor.putString("user.phone", user.getPhone());
            editor.putString("user.place", user.getPlace());
        }
        editor.commit();
    }
    public static void categoryView(final Context context, final Callback<Category> callback, final String id) {
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
        "54cc7e2722f5cf8e0407e101");
    }

    /**
     * Sample IDs = [ 54cba1e196d6b2930f431f79, 54cba20a96d6b29d0f431f7c, 54cba22696d6b29a0f431f83 ]
     * @param context
     * @param callback
     * @param id
     */
    public static void adDetails(final Context context, final Callback<Ad> callback, final String id) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            Ad ad = AdBuilder.build(response.getContent());
                            ad.setId(id);
                            callback.onSuccess(ad);
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }
                }
            }
        }, Arrays.asList(mockAdIds).contains(id) ? id : mockAdIds[0]);
    }

    public static void adsList(final Context context, final Callback<Subcategory> callback, final String subcategoryId) {
        WebConnection.getInstance().request(new WebConnection.Callback() {
            @Override
            public void onResponse(WebConnection.Response response) {
                if (callback != null) {
                    switch (response.getStatus()) {
                        case WebConnection.Status.OK:
                            Subcategory scat = SubcategoryBuilder.build(response.getContent());
                            scat.setId(subcategoryId);
                            callback.onSuccess(scat);
                            break;
                        default:
                            callback.onFailed(context.getResources().getString(R.string.net_err));
                            break;
                    }
                }
            }
        }, "54cc6e1322f5cf830307e0f0");
    }
}
