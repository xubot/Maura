package com.example.administrator.maura.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.maura.NewsDetailsActivity;
import com.example.administrator.maura.R;
import com.example.administrator.maura.http.Fault;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

/**
 *给tablayout下边  用来展示对应数据的类
 */

@SuppressLint("ValidFragment")
public class NewsTabLouyoutVauleFragment extends Fragment{

    @BindView(R.id.recyclerView_news)
    RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private NewsLoader mNewsLoader;
    private Unbinder binder;

    private  List<NewsBean.ResultBean.DataBean> newsDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout_vaule_fragment, null);
        binder = ButterKnife.bind(this, view);

        mNewsLoader = new NewsLoader();

        //得到newsFragment传来的请求参数
        Bundle bundle = getArguments();
        if(null != bundle){
            String vaule = bundle.getString("tabValue");
            initView(vaule);
        }

        return view;
    }

    private void initView(String vaule) {
        mRecyclerView.addItemDecoration(new NewsDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mNewsAdapter = new NewsAdapter(getContext());
        mRecyclerView.setAdapter(mNewsAdapter);

        mNewsAdapter.setonItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("zzz","点击条目："+position);
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("newsUrl",newsDetails.get(position).getUrl());
                startActivity(intent);
            }
        });

        getNewsList(vaule);
    }

    /**
     * 获取新闻列表
     */
    private void getNewsList(String vaule){
        Subscription NewsSubscription = mNewsLoader.getNewsList(vaule, "b0037a6093f6d082499ddc795ce72b9c").subscribe(new Action1<List<NewsBean.ResultBean.DataBean>>() {
            @Override
            public void call(List<NewsBean.ResultBean.DataBean> newsMagessBeans) {
                Log.e("zzz", "message:" + newsMagessBeans.size());
                newsDetails=newsMagessBeans;
                mNewsAdapter.setMovies(newsMagessBeans);
                mNewsAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("zzz", "error message:" + throwable.getMessage());
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
        //addSubscription(NewsSubscription);//待定
    }

    public static class NewsDecoration extends RecyclerView.ItemDecoration{
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