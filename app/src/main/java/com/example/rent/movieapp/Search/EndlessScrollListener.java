package com.example.rent.movieapp.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.rent.movieapp.listing.ListingActivity;
import com.example.rent.movieapp.listing.ListingPresenter;
import com.example.rent.movieapp.main.CurrentItemListener;
import com.example.rent.movieapp.main.OnLoadNextPageListener;
import com.example.rent.movieapp.main.ShowOrHideCounter;

/**
 * Created by RENT on 2017-03-11.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private static final int PAGE_SIZE = 10;

    private double totalItemsNumber;

    private LinearLayoutManager layoutManager;

    private boolean isLoading;

    private OnLoadNextPageListener onLoadNextPageListener;

    private CurrentItemListener currentItemListener;

    private ShowOrHideCounter showOrHideCounter;

    private boolean isCounterShown;

    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadNextPageListener onLoadNextPageListener) {
        this.layoutManager = layoutManager;
        this.onLoadNextPageListener = onLoadNextPageListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int alreadyLoadedItems = layoutManager.getItemCount();
        int currentPage = (int) Math.ceil(alreadyLoadedItems / PAGE_SIZE);
        double numberOfAllPages = Math.ceil(totalItemsNumber / PAGE_SIZE);
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1;

        if (currentPage < numberOfAllPages && lastVisibleItemPosition ==
                alreadyLoadedItems && !isLoading) {
            loadNextPage(++currentPage);
            isLoading = true;
        }

        if (currentItemListener != null) {
            currentItemListener.onNewCurrentItem(lastVisibleItemPosition, totalItemsNumber);
        }

        Log.d("result", "lastVisibleItemPosition " + lastVisibleItemPosition);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (showOrHideCounter != null) {
            if (isCounterShown && newState == RecyclerView.SCROLL_STATE_IDLE) {
                recyclerView.postDelayed(() -> {
                    showOrHideCounter.hideCounter();
                    isCounterShown = false;
                }, 3000);
            } else if (!isCounterShown && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                showOrHideCounter.showCounter();
                isCounterShown = true;
            }
        }
    }

    private void loadNextPage(int pageNumber) {
        onLoadNextPageListener.loadNextPage(pageNumber);
    }

    public void setTotalItemsNumber(int totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
        isLoading = false;
    }

    public void setShowOrHideCounter(ShowOrHideCounter showOrHideCounter) {
        this.showOrHideCounter = showOrHideCounter;
    }

    public void setCurrentItemListener(CurrentItemListener currentItemListener) {
        this.currentItemListener = currentItemListener;
    }
}

