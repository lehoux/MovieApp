package com.example.rent.movieapp;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import nucleus.presenter.Presenter;

/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {

    public void getDataAsync(String title) {

        new Thread() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    String result = getData(title);
                    MovieResult searchResult = new Gson().fromJson(result, MovieResult.class);
                    getView().setDataOnUiThread(searchResult, false);
                } catch (IOException e) {
                    getView().setDataOnUiThread(null, true);

                }
            }
        }.start();
    }

    public String getData(String title) throws IOException {

        String stringUrl = "https://www.omdbapi.com/?s=" + title;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(3000);
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);

    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}