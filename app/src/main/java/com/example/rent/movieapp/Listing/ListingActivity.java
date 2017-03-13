package com.example.rent.movieapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.rent.movieapp.R;
import com.example.rent.movieapp.main.CurrentItemListener;
import com.example.rent.movieapp.main.RetrofitProvider;
import com.example.rent.movieapp.main.ShowOrHideCounter;
import com.example.rent.movieapp.search.EndlessScrollListener;
import com.example.rent.movieapp.search.MovieResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

/**
 * Created by RENT on 2017-03-07.
 */

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusActivity<ListingPresenter> implements CurrentItemListener, ShowOrHideCounter {


    public static final int NO_YEAR_SELECTED = -1;
    private static final String SEARCH_YEAR = "search_year";
    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_TYPE = "search_type";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet_image_view)
    ImageView noInternetImage;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.no_result)
    FrameLayout noResults;
    @BindView(R.id.counter)
    TextView counterTextView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieListAdapter adapter;
    private EndlessScrollListener endlessScrollListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);
        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        getPresenter().setRetrofit(retrofitProvider.provideRetrofit());
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        adapter = new MovieListAdapter();
//        adapter.setOnMovieItemClickListener(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        endlessScrollListener = new EndlessScrollListener(layoutManager, getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(endlessScrollListener);
        endlessScrollListener.setCurrentItemListener(this);
        endlessScrollListener.setShowOrHideCounter(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startLoading(title, year, type);
            }
        });
        startLoading(title, year, type);
    }

    private void startLoading(String title, int year, String type) {
        getPresenter().getDataAsync(title, year, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::Success, this::Error);
    }

    @OnClick(R.id.no_internet_image_view)
    public void onNoInternetImageViewClick(View view) {
        Toast.makeText(this, "Kliknąłem no internet image view", Toast.LENGTH_LONG).show();
    }

    private void Error(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    public void appendItems(MovieResult movieResult) {
        adapter.addItems(movieResult.getItems());
        endlessScrollListener.setTotalItemsNumber(Integer.parseInt(movieResult.getTotalResults()));
    }

    private void Success(MovieResult movieResult) {
        swipeRefreshLayout.setRefreshing(false);
        if ("False".equalsIgnoreCase(movieResult.getResponse())) {

            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        } else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(swipeRefreshLayout));
            adapter.setItems(movieResult.getItems());
            endlessScrollListener.setTotalItemsNumber(Integer.parseInt(movieResult.getTotalResults()));
        }
    }

    public static Intent createIntent(Context context, String title, int year, String typeKey) {

        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, typeKey);
        return intent;
    }

    @Override
    public void onNewCurrentItem(int currentItem, double totalItemsCount) {
        counterTextView.setText(currentItem + "/" + totalItemsCount);
    }

    @Override
    public void showCounter() {
        counterTextView.setVisibility(View.VISIBLE);
        counterTextView.animate().translationX(0).start();
    }

    @Override
    public void hideCounter() {
        counterTextView.animate().translationX(counterTextView.getWidth() * 2).start();
    }

    public void onMovieItemClickListener(String imdbID) {
        startActivity(DetailActivity.createIntent(this, imdbID));

    }
}

