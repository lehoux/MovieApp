package com.example.rent.movieapp.search;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.example.rent.movieapp.R;
import com.example.rent.movieapp.listing.ListingActivity;
import com.example.rent.movieapp.listing.MovieItem;
import com.example.rent.movieapp.main.RetrofitProvider;
import com.example.rent.movieapp.main.SearchService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class SearchActivity extends AppCompatActivity {

    private Map<Integer, String> apiKeysMap = new HashMap<Integer, String>() {{
        put(R.id.radio_movies, "movie");
        put(R.id.radio_episodes, "episode");
        put(R.id.radio_games, "game");
        put(R.id.radio_series, "series");
    }};

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;

    @BindView(R.id.edit_text)
    TextInputEditText editText;

    @BindView(R.id.search_button)
    ImageView searchButton;

    @BindView(R.id.year_checkBox)
    CheckBox yearCheckbox;

    @BindView(R.id.type_checkbox)
    CheckBox typeCheckbox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();

        PosterRecyclerViewAdapter posterRecyclerViewAdapter = new PosterRecyclerViewAdapter();
        searchRecyclerView.setAdapter(posterRecyclerViewAdapter);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setValue(year);
        numberPicker.setWrapSelectorWheel(true);

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SearchService searchService = retrofitProvider.provideRetrofit().create(SearchService.class);
        searchService.search(1, "a*", "2017", null)
                .flatMap(movieResult -> Observable.fromIterable(movieResult.getItems()))
                .map(MovieItem::getPoster)
                .filter(s -> !"n/a".equalsIgnoreCase(s))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posterRecyclerViewAdapter::setUrls, throwable -> {
                    // nop
                });

    }

    @OnCheckedChanged(R.id.type_checkbox)
    void onTypeCheckboxStateChecked(CompoundButton buttonView, boolean isChecked) {
        radioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);

    }

    @OnCheckedChanged(R.id.year_checkBox)
    void onYearCheckboxStateChanged(CompoundButton buttonView, boolean isChecked) {
        numberPicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);

    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        int checkRadioId = radioGroup.getCheckedRadioButtonId();
        String typeKey = typeCheckbox.isChecked() ? apiKeysMap.get(checkRadioId) : null;
        int year = yearCheckbox.isChecked() ? numberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;

        startActivity(ListingActivity.createIntent(SearchActivity.this,
                editText.getText().toString(), year, typeKey));

    }
}
