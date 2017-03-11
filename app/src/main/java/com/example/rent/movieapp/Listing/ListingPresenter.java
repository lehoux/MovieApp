package com.example.rent.movieapp.listing;

import com.example.rent.movieapp.main.SearchService;
import com.example.rent.movieapp.search.MovieResult;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {


    private MovieResult movieResultOfAllItemsSearched;
    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;

    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://omdbapi.com/")
                .build();
    }

    public Observable<MovieResult> getDataAsync(String title, int year, String type) {

        this.title = title;
        this.type = type;
        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);

        return retrofit.create(SearchService.class).search(1, title, stringYear, type);
    }


    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    public void loadNextPage(int page) {
        retrofit.create(SearchService.class).search(1, title, stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResult -> {
                    getView().appendItems(movieResult);
                }, throwable -> {
                });
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
