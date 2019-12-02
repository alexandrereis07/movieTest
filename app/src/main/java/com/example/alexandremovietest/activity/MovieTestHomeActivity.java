package com.example.alexandremovietest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.alexandremovietest.R;
import com.example.alexandremovietest.adapter.MoviesAdapter;
import com.example.alexandremovietest.database.controller.DisplayDAO;
import com.example.alexandremovietest.database.model.Display;
import com.example.alexandremovietest.model.Genre;
import com.example.alexandremovietest.model.Genres;
import com.example.alexandremovietest.model.Movie;
import com.example.alexandremovietest.model.Movies;
import com.example.alexandremovietest.network.GenresService;
import com.example.alexandremovietest.network.MoviesService;
import com.example.alexandremovietest.network.RetrofitInitializer;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTestHomeActivity extends AppCompatActivity {

    List<Genre> mListGenres;
    RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    TabLayout tabLayout;
    ProgressBar mProgressBar;
    DisplayDAO mDao;
    Display mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_test_home);

        buildRecyclerMovies();
        callServiceGenres();

    }


    //BUILDERS
    private void buildRecyclerMovies() {

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        showProgress(true);

        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mMoviesAdapter = new MoviesAdapter(new ArrayList<Movie>(0), MovieTestHomeActivity.this);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);

        mDao = new DisplayDAO(MovieTestHomeActivity.this);
        mDisplay =  mDao.getTypeDisplay();
        GridLayoutManager layoutManager = new GridLayoutManager(this, mDisplay.getDisplayType());
        mMoviesRecyclerView.setLayoutManager(layoutManager);
    }

    private void buildGenresTabs() {
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);

        for (Genre genre: mListGenres) {
            tabLayout.addTab(tabLayout.newTab().setText(genre.getName()));
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                callServiceMoviesByGenre(mListGenres.get(tab.getPosition()));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    //SERVICES
    private void callServiceGenres(){
        GenresService service = RetrofitInitializer.getRetrofitInstance().create(GenresService.class);
        Call<Genres> call = service.getGenres();
        call.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                if(response.isSuccessful()){
                    mListGenres = response.body().getListGenres();
                    buildGenresTabs();
                    callServiceMoviesByGenre(mListGenres.get(0));
                }
            }
            @Override
            public void onFailure(Call<Genres> call, Throwable t) {
                reject(t.getMessage());
                showProgress(false);
            }
        });

    }

    private void callServiceMoviesByGenre(Genre genre) {
        MoviesService service = RetrofitInitializer.getRetrofitInstance().create(MoviesService.class);
        Call<Movies> call = service.getMovies(genre.getId());
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                mMoviesAdapter.updateList(response.body().getListMovies(), mListGenres);
                showProgress(false);
            }
            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                reject(t.getMessage());
                showProgress(false);
            }
        });

    }

    private void reject(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MovieTestHomeActivity.this).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item = menu.add("Change display");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_apps_black_24dp);
        item.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item){

                        mDisplay.setDisplayType(mDisplay.getDisplayType() + 1);
                        if(mDisplay.getDisplayType() == 4){
                            mDisplay.setDisplayType(1);
                        }
                        mDao.update(mDisplay);

                        GridLayoutManager layoutManager = new GridLayoutManager(MovieTestHomeActivity.this, mDisplay.getDisplayType());
                        mMoviesRecyclerView.setLayoutManager(layoutManager);
                        return true;
                    }
                }
        );

        return super.onCreateOptionsMenu(menu);
    }


}
