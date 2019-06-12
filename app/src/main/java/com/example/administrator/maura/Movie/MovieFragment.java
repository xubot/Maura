package com.example.administrator.maura.Movie;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.maura.R;
import com.example.administrator.maura.SuperClass.BaseFragment;
import com.example.administrator.maura.http.Fault;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

public class MovieFragment extends BaseFragment {

    private Unbinder binder;
    private MovieLoader mMovieLoader;
    @BindView(R.id.recyclerView_moive)
    RecyclerView mRecyclerViewMoive;
    private MovieAdapter mMovieAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moive, container,false);
        binder = ButterKnife.bind(this, view);

        mMovieLoader = new MovieLoader();
        initView();
        return view;
    }

    private void initView() {
        mRecyclerViewMoive.addItemDecoration(new MoiveDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewMoive.setLayoutManager(manager);
        mRecyclerViewMoive.setAdapter(mMovieAdapter);
        getMovieList();
        mMovieAdapter = new MovieAdapter(getContext());
    }

    /**
     * 获取电影列表
     */
    private void getMovieList(){
        Subscription MovieSubscription = mMovieLoader.getMovie(0, 10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                mMovieAdapter.setMovies(movies);
                mMovieAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG", "error message:" + throwable.getMessage());
                if (throwable instanceof Fault) {
                    Fault fault = (Fault) throwable;
                    if (fault.getErrorCode() == 404) {
                        //错误处理
                    } else if (fault.getErrorCode() == 500) {
                        //错误处理
                    } else if (fault.getErrorCode() == 501) {
                        //错误处理
                    }
                }
            }
        });

        //addSubscription(MovieSubscription);//待定

    }


    public static class MoiveDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0,0,0,20);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }

}
