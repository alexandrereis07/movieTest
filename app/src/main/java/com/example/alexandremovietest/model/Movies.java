package com.example.alexandremovietest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("results")
    private List<Movie> listMovies;

    public Movies(int page, int total_results, int total_pages, List<Movie> listMovies) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.listMovies = listMovies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(List<Movie> listMovies) {
        this.listMovies = listMovies;
    }
}
