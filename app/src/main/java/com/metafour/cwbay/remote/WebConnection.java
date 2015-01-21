package com.metafour.cwbay.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by nadim on 1/21/15.
 */
public class WebConnection {
    public static class Status {
        public static final int OK = 200;
        public static final int EXISTS = 302;
        public static final int UNAUTHORIZED = 401;
        public static final int NOT_FOUND = 404;
        public static final int NET_ERROR = 1000;
    }
    public static interface Callback {
        public void onResponse(Response response);
    }

    public static class Response{
        public int status;
        public String content;

        public Response(int status, String content) {
            this.status = status;
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    private static final WebConnection instance = new WebConnection();
    private WebConnection() {}
    public static WebConnection getInstance() { return instance; }

    // Use these credentials for actions for which no user is yet available (e.g. create new user)
    private static final String DEFAULT_USER = "app";
    private static final String DEFAULT_PASS = "pass";

    private HttpClient client = null;
    private DigestScheme digest = null;
    private String basePath = null;

    private String user = null;
    private String pass = null;

    public synchronized void connect(final Callback callback, final String basePath) {
        if (client == null) {
            this.basePath = basePath;
            client = new DefaultHttpClient();
            (new AsyncTask<Callback, Void, Response>() {
                @Override
                protected Response doInBackground(Callback... params) {
                    String[] ret = new String[2];
                    try {
                        HttpGet get = new HttpGet(basePath);
                        HttpResponse response = client.execute(get);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                            Header authHeader = response.getFirstHeader(AUTH.WWW_AUTH);
                            digest = new DigestScheme();
                            digest.processChallenge(authHeader);

                            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS);
                            get.addHeader(digest.authenticate(creds, get));

                            response = client.execute(get);
                            return new Response(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
                        }
                    } catch (Exception e) {
                        return new Response(WebConnection.Status.NET_ERROR, "");
                    }
                    return new Response(WebConnection.Status.NET_ERROR, "");
                }

                @Override
                protected void onPostExecute(Response response) {
                    super.onPostExecute(response);
                    if (callback != null) {
                        callback.onResponse(response);
                    }
                }
            }).execute(callback);
        }
    }

    public void setCredentials(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void request(final Callback callback, final String path, final String content) {
        (new AsyncTask<Callback, Void, Response>() {
            @Override
            protected Response doInBackground(Callback... params) {
                try {
                    HttpPost post = new HttpPost(basePath + path);
                    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user == null ? DEFAULT_USER : user, pass == null ? DEFAULT_PASS : pass);
                    post.addHeader(digest.authenticate(creds, post));
                    if (content != null) post.setEntity(new ByteArrayEntity(content.getBytes()));
                    HttpResponse response = client.execute(post);
                    return new Response(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
                } catch (Exception e) {
                    //Log.i("CWBay", e.getMessage());
                    return new Response(WebConnection.Status.NET_ERROR, e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);
                if (callback != null) {
                    callback.onResponse(response);
                }
            }
        }).execute(callback);

    }

    public void request(final Callback callback, final String path) {
        request(callback, path, null);
    }
}
