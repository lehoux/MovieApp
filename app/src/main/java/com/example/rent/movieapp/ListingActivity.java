package com.example.rent.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by RENT on 2017-03-07.
 */

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";
    private MovieListAdapter adapter;
    private ViewFlipper viewFlipper;
    private ImageView noInternetImage;
    private RecyclerView recyclerView;

    public static Intent createIntent(Context context, String title) {

        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        getPresenter().getDataAsync(title);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MovieListAdapter();
        recyclerView.setAdapter(adapter);

        noInternetImage = (ImageView) findViewById(R.id.no_internet_image_view);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

        getPresenter().getDataAsync(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::Success, this::Error);
    }

    private void Error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void Success(MovieResult movieResult) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
        adapter.setItems(movieResult.getItems());
    }
}

//    public void setDataOnUiThread(MovieResult result, boolean isProblemWithInternet) {
//
//        runOnUiThread(() -> {
//            if (isProblemWithInternet) {
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
//            } else {
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
//                adapter.setItems(result.getItems());
//            }
//        });

