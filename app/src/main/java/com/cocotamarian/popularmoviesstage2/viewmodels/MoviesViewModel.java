package com.cocotamarian.popularmoviesstage2.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cocotamarian.popularmoviesstage2.database.MovieRepository;
import com.cocotamarian.popularmoviesstage2.model.MovieResult;

public class MoviesViewModel extends ViewModel {
    private LiveData<MovieResult> results;

    MoviesViewModel(String filter, String apiKey) {
        loadFromNetwork(filter, apiKey);
    }

    public void loadFromNetwork(String filter, String apiKey) {
        results = MovieRepository.getInstance().getMovies(filter, apiKey);
    }

    public LiveData<MovieResult> getResults() {
        return results;
    }
}