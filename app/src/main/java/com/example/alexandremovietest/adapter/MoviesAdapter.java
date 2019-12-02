package com.example.alexandremovietest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alexandremovietest.R;
import com.example.alexandremovietest.Util.Constants;
import com.example.alexandremovietest.Util.DownloadImageTask;
import com.example.alexandremovietest.component.AlertDialogMovieDetails;
import com.example.alexandremovietest.model.Genre;
import com.example.alexandremovietest.model.Movie;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private  List<Movie> mListMovies;
    private  List<Genre> mListGenres;
    private Context mContext;

    public MoviesAdapter(List<Movie> listMovies, Context context) {
        this.mListMovies = listMovies;
        this.mContext = context;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                new AlertDialogMovieDetails(mListMovies.get(position), mContext , mListGenres).showDialogMovie();
            }
        });

        new DownloadImageTask(holder.posterPath).execute(Constants.IMG_PATH+mListMovies.get(position).getPosterPath());
        holder.title.setText(mListMovies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mListMovies != null ? mListMovies.size() : 0;
    }

    public void updateList(List<Movie> list, List<Genre> listGenres) {
        mListMovies = list;
        mListGenres = listGenres;
        notifyDataSetChanged();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView posterPath;
        public TextView title;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            posterPath = (ImageView) itemView.findViewById(R.id.posterPath);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }
}
