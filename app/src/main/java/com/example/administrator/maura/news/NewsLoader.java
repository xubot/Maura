package com.example.administrator.maura.news;

import com.example.administrator.maura.http.ObjectLoader;
import com.example.administrator.maura.http.RetrofitServiceManager;
import com.example.administrator.maura.news.NewsBean.ResultBean.DataBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;


/**
 *
 *
 * Created by zhouwei on 16/11/10.
 */

public class NewsLoader extends ObjectLoader {
    private NewsService mNewsService;

    public NewsLoader(){
        mNewsService = RetrofitServiceManager.getInstance().create(NewsService.class);
    }


    public Observable<List<DataBean>> getNewsList(String cityId, String key){
        return observe(mNewsService.getNews(cityId,key)).map(new Func1<NewsBean, List<DataBean>>() {
            @Override
            public List<DataBean> call(NewsBean newsBean) {
                return newsBean.getResult().getData();
            }
        });
    }


    public interface NewsService{
        //获取新闻列表
        @GET("index")
        Observable<NewsBean> getNews(@Query("type") String cityId, @Query("key") String key);
    }
}
