package com.example.administrator.maura.Movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.maura.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zhouwei on 16/11/16.
 */

public class MovieAdapter extends RecyclerView.Adapter {
    private List<Movie> mMovies;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }


    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.moive_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        Glide.with(context).load(movie.images.small).into(movieHolder.mImageView);
        movieHolder.time.setText("上映时间："+movie.year + "年");
        movieHolder.title.setText(movie.title);
        movieHolder.subTitle.setText(movie.original_title);

    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0:mMovies.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_image)
        ImageView mImageView;
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_sub_title)
        TextView subTitle;
        @BindView(R.id.movie_time)
        TextView time;
        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
