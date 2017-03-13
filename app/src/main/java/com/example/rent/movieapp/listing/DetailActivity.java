package com.example.rent.movieapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rent.movieapp.R;
import com.example.rent.movieapp.main.RetrofitProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.view.NucleusActivity;

/**
 * Created by RENT on 2017-03-13.
 */

public class DetailActivity extends NucleusActivity<DetailPresenter> {

    private static final String ID_KEY = "id_key" ;
    private Disposable subscribe;

    @BindView(R.id.poster)
    ImageView poster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String imdbID = getIntent().getStringExtra(ID_KEY);

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        getPresenter().setRetrofit(retrofitProvider.provideRetrofit());

        subscribe = getPresenter().loadDetail(imdbID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::error);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe !=null) {
            subscribe.dispose();
        }
    }

    private void success(MovieDetails movieDetails) {
        Glide.with(this).load(movieDetails.getPoster()).into(poster);
    }

    private void error(Throwable throwable) {

    }

    public static Intent createIntent(Context context, String imdbID) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID_KEY, imdbID);
        return intent;
    }
}
