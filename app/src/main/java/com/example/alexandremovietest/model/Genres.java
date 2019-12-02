package com.example.alexandremovietest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genres {

    @SerializedName("genres")
    private List<Genre> listGenres;

    public Genres(List<Genre> listGenres) {
        this.listGenres = listGenres;
    }

    public List<Genre> getListGenres() {
        return listGenres;
    }

    public void setListGenres(List<Genre> listGenres) {
        this.listGenres = listGenres;
    }
}
