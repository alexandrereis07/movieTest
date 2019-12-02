package com.example.alexandremovietest.component;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexandremovietest.R;
import com.example.alexandremovietest.Util.Constants;
import com.example.alexandremovietest.Util.DownloadImageTask;
import com.example.alexandremovietest.model.Genre;
import com.example.alexandremovietest.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AlertDialogMovieDetails {

    private Movie mMovie;
    private Context mContext;
    private AlertDialog alertDialogCongratulations;
    private List<Genre> mListGenres;

    public AlertDialogMovieDetails(Movie mMovie, Context mContext, List<Genre> listGenres) {
        this.mMovie = mMovie;
        this.mContext = mContext;
        this.mListGenres = listGenres;
    }

    public void showDialogMovie(){
        LayoutInflater li = LayoutInflater.from(mContext);
        View view = li.inflate(R.layout.movie_alert_dialog_datail, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        alertDialogBuilder.setView(view);

        TextView title = view.findViewById(R.id.title);
        title.setText(mMovie.getTitle());

        ImageView poster = view.findViewById(R.id.imageMovie);
        new DownloadImageTask(poster).execute(Constants.IMG_PATH+mMovie.getPosterPath());

        TextView genre = view.findViewById(R.id.genre);
        genre.setText(concatGenres());

        TextView overview = view.findViewById(R.id.overview);
        overview.setText(mMovie.getOverview());

        Button ok = view.findViewById(R.id.btnOK);
        ok.setText("OK");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogCongratulations.dismiss();
            }
        });


        alertDialogCongratulations = alertDialogBuilder.create();
        alertDialogCongratulations.show();
        alertDialogCongratulations.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private String concatGenres() {
        String strGenresMovie = "";
        for (Genre genre: mListGenres) {
            for (int i = 0; i < mMovie.getGenreIds().length; i++){
                if (genre.getId() == mMovie.getGenreIds()[i]){
                    if(mMovie.getGenreIds()[mMovie.getGenreIds().length - 1] == mMovie.getGenreIds()[i]){
                        strGenresMovie = strGenresMovie.concat(genre.getName() + ". ");
                    }else {
                        strGenresMovie = strGenresMovie.concat(genre.getName() + ", ");
                    }
                }
            }
        }
        return strGenresMovie;
    }


}
