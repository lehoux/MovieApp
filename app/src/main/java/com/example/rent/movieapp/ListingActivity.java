package com.example.rent.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by RENT on 2017-03-07.
 */

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusActivity<ListingPresenter> {


    private static final String SEARCH_YEAR = "search_year";
    private static final String SEARCH_TITLE = "search_title";
    public static final int NO_YEAR_SELECTED = -1;
    private static final String SEARCH_TYPE = "search_type";


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet_image_view)
    ImageView noInternetImage;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    private MovieListAdapter adapter;
    int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);

    @BindView(R.id.no_result)
    FrameLayout noResults;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);
        recyclerView.setAdapter(adapter);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        adapter = new MovieListAdapter();
        recyclerView.setAdapter(adapter);

        getPresenter().getDataAsync(title, year, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::Success, this::Error);
    }

    public static Intent createIntent(Context context, String title, int year, String typeKey) {

        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, typeKey);
        return intent;
    }


    private void Error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void Success(MovieResult movieResult) {
        if ("False".equals(movieResult.getResponse())) {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        } else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
            adapter.setItems(movieResult.getItems());
        }
    }
}

