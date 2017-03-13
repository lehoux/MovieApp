package com.example.rent.movieapp.listing;

import com.example.rent.movieapp.main.DetailService;
import com.example.rent.movieapp.main.RetrofitProvider;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-13.
 */

public class DetailPresenter extends Presenter<DetailActivity> {

    private Retrofit retrofit;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    protected void onTakeView(DetailActivity detailActivity) {
        super.onTakeView(detailActivity);
        RetrofitProvider retrofitProvider = (RetrofitProvider) detailActivity.getApplication();
        retrofit = retrofitProvider.provideRetrofit();
    }



    public Observable<MovieDetails> loadDetail(String imdbID) {
        DetailService detailService = retrofit.create(DetailService.class);
        return detailService.getDetailInfo(imdbID);
    }
}
