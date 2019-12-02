package com.example.alexandremovietest.network;

import com.example.alexandremovietest.Util.Constants;
import com.example.alexandremovietest.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("/3/discover/movie?api_key="+Constants.API_KEY+"&language=pt-br")
    Call<Movies> getMovies(@Query("with_genres") int idGenre);

}
