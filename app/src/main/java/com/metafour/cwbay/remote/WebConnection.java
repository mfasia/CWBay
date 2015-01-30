package com.metafour.cwbay.remote;

import android.os.AsyncTask;

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

/**
 * Created by nadim on 1/21/15.
 */
public class WebConnection {
    private static final String MOCK_BASE = "http://www.mocky.io/v2/";

    public static class Status {
        public static final int OK = 200;
        public static final int UNAUTHORIZED = 401;
        public static final int NOT_FOUND = 404;
        public static final int EXISTS = 409;
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
    private WebConnection() {
        basePath = MOCK_BASE;
        client = new DefaultHttpClient();
    }
    public static WebConnection getInstance() { return instance; }

    private HttpClient client;
    private String basePath;

    public void request(final Callback callback, final String path, final String content) {
        (new AsyncTask<Callback, Void, Response>() {
            @Override
            protected Response doInBackground(Callback... params) {
                try {
                    HttpPost post = new HttpPost(basePath + path);
                    if (content != null) post.setEntity(new ByteArrayEntity(content.getBytes()));
                    HttpResponse response = client.execute(post);
                    return new Response(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
                } catch (Exception e) {
                    return new Response(WebConnection.Status.NET_ERROR, "");
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
