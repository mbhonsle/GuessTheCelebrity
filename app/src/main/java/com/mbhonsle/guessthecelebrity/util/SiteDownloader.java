package com.mbhonsle.guessthecelebrity.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by mak on 12/13/17.
 */

public class SiteDownloader extends AsyncTask<String, Void, String> {

    private String url = "";

    SiteDownloader(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection httpURLConnection;

        try {
            url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(httpURLConnection.getInputStream());
            int data = reader.read();
            while(data != -1) {
                result.append((char) data);
                data = reader.read();
            }
        } catch (Exception e) {
            Log.e("ERROR:",  e.getMessage());
        }
        return result.toString();
    }


    public String fetch() throws ExecutionException, InterruptedException {
        return this.execute(this.url).get();
    }
}
