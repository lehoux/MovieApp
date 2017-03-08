package com.example.rent.movieapp;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;

    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://omdbapi.com/")
                .build();

    }

    public Observable<MovieResult> getDataAsync(String title) {

        return retrofit.create(SearchService.class).search(title);
    }
}


//        new Thread() {
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void run() {
//                try {
//                    String result = getData(title);
//                    MovieResult searchResult = new Gson().fromJson(result, MovieResult.class);
//                    getView().setDataOnUiThread(searchResult, false);
//                } catch (IOException e) {
//                    getView().setDataOnUiThread(null, true);
//
//                }
//            }
//        }.start();
//    }
//
//    public String getData(String title) throws IOException {
//
//        String stringUrl = "https://www.omdbapi.com/?s=" + title;
//        URL url = new URL(stringUrl);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setConnectTimeout(3000);
//        InputStream inputStream = urlConnection.getInputStream();
//        return convertStreamToString(inputStream);
//
//    }
//
//    private String convertStreamToString(java.io.InputStream is) {
//        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//        return s.hasNext() ? s.next() : "";
//    }
//}
