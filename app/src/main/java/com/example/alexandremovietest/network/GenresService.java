package com.example.alexandremovietest.network;

import com.example.alexandremovietest.Util.Constants;
import com.example.alexandremovietest.model.Genres;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenresService {

    @GET("/3/genre/movie/list?api_key="+Constants.API_KEY+"&language=pt-br")
    Call<Genres> getGenres();

}
