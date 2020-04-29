package com.cocotamarian.popularmoviesstage2.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cocotamarian.popularmoviesstage2.R;
import com.cocotamarian.popularmoviesstage2.adapter.CastsAdapter;
import com.cocotamarian.popularmoviesstage2.adapter.ReviewsAdapter;
import com.cocotamarian.popularmoviesstage2.adapter.TrailersAdapter;
import com.cocotamarian.popularmoviesstage2.database.MovieRepository;
import com.cocotamarian.popularmoviesstage2.databinding.ActivityDetailBinding;
import com.cocotamarian.popularmoviesstage2.model.Cast;
import com.cocotamarian.popularmoviesstage2.model.FavouriteMovie;
import com.cocotamarian.popularmoviesstage2.model.Movie;
import com.cocotamarian.popularmoviesstage2.model.Review;
import com.cocotamarian.popularmoviesstage2.model.Trailer;
import com.cocotamarian.popularmoviesstage2.utils.AppConstants;
import com.cocotamarian.popularmoviesstage2.utils.DateUtils;
import com.cocotamarian.popularmoviesstage2.utils.NetworkUtils;
import com.cocotamarian.popularmoviesstage2.viewmodels.DetailsViewModel;
import com.cocotamarian.popularmoviesstage2.viewmodels.DetailsViewModelFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.ItemClickListener {

    private ActivityDetailBinding mDetailBinding;
    private boolean isFavourite;
    private CastsAdapter mCastsAdapter;
    private TrailersAdapter mTrailersAdapter;
    private ReviewsAdapter mReviewsAdapter;
    private String mVideoKey;
    private String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Movie movie = (Movie) intent.getExtras().getSerializable(getString(R.string.movie_object));
            if (movie != null) {
                populateUi(movie);
                setupViewModel(movie);
            }
        }
    }

    private void populateUi(Movie movie) {
        movieName = movie.getOriginalTitle();
        setTitle(movie.getOriginalTitle());
        Glide.with(this).asDrawable().apply(new RequestOptions().placeholder(R.drawable.placeholder_movieimage)
                .error(R.drawable.error_placeholder))
                .load( AppConstants.BASE_IMAGE_URL_500 + movie.getBackdropPath()).into(mDetailBinding.backdropIv);
        Glide.with(this).asDrawable().apply(new RequestOptions().placeholder(R.drawable.placeholder_movieimage)
                .error(R.drawable.error_placeholder))
                .load(AppConstants.BASE_IMAGE_URL_342 + movie.getPosterUrl()).into(mDetailBinding.movieThumbnailIv);
        mDetailBinding.originalTitleTv.setText(movie.getOriginalTitle());
        mDetailBinding.originalTitleTv.startAnimation( AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom));
        mDetailBinding.plotSynopsisTv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom));
        mDetailBinding.plotSynopsisTv.setText(movie.getOverview());
        mDetailBinding.voteAverageTv.setText(String.format("%s / 10", movie.getVoteAverage()));
        mDetailBinding.voteAverageTv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.enter_from_right));
        mDetailBinding.releaseDateTv.setText( DateUtils.getFormattedDate(movie.getReleaseDate()));
        mDetailBinding.releaseDateTv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.enter_from_right));
        mCastsAdapter = new CastsAdapter(this);
        mTrailersAdapter = new TrailersAdapter(this, this);
        mReviewsAdapter = new ReviewsAdapter(this);

        if (NetworkUtils.getConnectivityStatus(this)) {
            mDetailBinding.extraDetailsCl.setVisibility( View.GONE);
        }
    }

    private void setupViewModel(final Movie movie) {
        DetailsViewModelFactory factory = new DetailsViewModelFactory(movie.getMovieId(), getString(R.string.api_key));
        DetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);
        viewModel.getFavourite().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    isFavourite = integer != 0;
                }
                mDetailBinding.favouriteFab.setImageDrawable(isFavourite ? ContextCompat.
                        getDrawable(DetailActivity.this, R.drawable.ic_favorite_24dp) : ContextCompat.
                        getDrawable(DetailActivity.this, R.drawable.ic_favorite_border_24dp));
                Gson gson = new Gson();
                String json = gson.toJson(movie);
                final FavouriteMovie favouriteMovie = gson.fromJson(json, FavouriteMovie.class);
                mDetailBinding.favouriteFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleFavouriteIcon((FloatingActionButton) v);
                        MovieRepository.getInstance().refreshFavouriteMovies(favouriteMovie, isFavourite);
                        if (isFavourite) {
                            Toast.makeText(DetailActivity.this, getString(R.string.removed_from_favourite),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailActivity.this, getString(R.string.added_to_favourite),
                                    Toast.LENGTH_LONG).show();
                        }
                        isFavourite = !isFavourite;
                    }
                });
            }
        });
        populateCasts(viewModel);
        populateTrailers(viewModel);
        populateReviews(viewModel);
    }

    private void populateReviews(DetailsViewModel viewModel) {
        RecyclerView trailerRecyclerView = mDetailBinding.reviewsRv;
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setAdapter(mReviewsAdapter);
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if (reviews != null) {
                    mReviewsAdapter.setReviews(reviews);
                } else {
                    mReviewsAdapter.setReviews(new ArrayList<Review>());
                }
            }
        });
    }

    private void populateTrailers(DetailsViewModel viewModel) {
        RecyclerView trailerRecyclerView = mDetailBinding.trailersRv;
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setAdapter(mTrailersAdapter);
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                if (trailers != null) {
                    mTrailersAdapter.setTrailers(trailers);
                    mVideoKey = trailers.get(0).getVideoKey();
                } else {
                    mTrailersAdapter.setTrailers(null);
                }
            }
        });
    }

    private void populateCasts(DetailsViewModel viewModel) {
        RecyclerView castRecyclerView = mDetailBinding.castListRv;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        castRecyclerView.setHasFixedSize(true);
        castRecyclerView.setLayoutManager(layoutManager);
        castRecyclerView.setAdapter(mCastsAdapter);
        viewModel.getCasts().observe(this, new Observer<List<Cast>>() {
            @Override
            public void onChanged(@Nullable List<Cast> casts) {
                if (casts != null) {
                    mCastsAdapter.setCasts(casts);
                } else {
                    mCastsAdapter.setCasts(null);
                }
            }
        });
    }

    private void toggleFavouriteIcon(FloatingActionButton v) {
        if (isFavourite) {
            v.setImageDrawable(ContextCompat.
                    getDrawable(DetailActivity.this, R.drawable.ic_favorite_border_24dp));
        } else {
            v.setImageDrawable(ContextCompat.
                    getDrawable(DetailActivity.this, R.drawable.ic_favorite_24dp));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share_trailer) {
            shareTrailer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareTrailer() {
        String trailerUrl = AppConstants.YOUTUBE_URL + mVideoKey;
        String builder = "Watch Trailer: " + movieName + "\n" + trailerUrl;
        ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(getString(R.string.chooser_title))
                .setType("text/plain")
                .setText(builder).startChooser();
    }

    @Override
    public void onClick(String videoKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_APP_URI + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_URL + videoKey));
        if (appIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appIntent);
        } else if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        } else {
            Toast.makeText(this, getString(R.string.video_error), Toast.LENGTH_LONG).show();
        }
    }
}
