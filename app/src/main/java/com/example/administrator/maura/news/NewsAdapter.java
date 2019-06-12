package com.example.administrator.maura.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.maura.R;
import com.example.administrator.maura.news.NewsBean.ResultBean.DataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zhouwei on 16/11/16.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> implements View.OnClickListener {
    private Context context;
    private List<DataBean> mNewsDate;

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    //声明自定义的监听接口
    private OnItemClickListener monItemClickListener=null;

    //提供set方法
    public void setonItemClickListener(OnItemClickListener listener){
        this.monItemClickListener=listener;
    }


    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<DataBean> newsDate) {
        this.mNewsDate = newsDate;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null);
        NewsHolder newsHolder = new NewsHolder(inflate);
        //绑定监听事件
        inflate.setOnClickListener(this);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
        DataBean newsDataBean = mNewsDate.get(position);
        Glide.with(context)
                .load(newsDataBean.getThumbnail_pic_s())
                .centerCrop()
                .into(newsHolder.mImageView);
        newsHolder.time.setText("上映时间："+newsDataBean.getDate() + "年");
        newsHolder.title.setText(newsDataBean.getTitle());
        newsHolder.subTitle.setText(newsDataBean.getAuthor_name());
        //将position保存在itemView的Tag中，以便点击时进行获取
        newsHolder.itemView.setTag(position);
    }


    @Override
    public void onClick(View v) {
        //这里使用getTag方法获取position
        monItemClickListener.onItemClick(v, (int) v.getTag());
    }


    @Override
    public int getItemCount() {
        return mNewsDate == null ? 0:mNewsDate.size();
    }


    public static class NewsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.news_image)
        ImageView mImageView;
        @BindView(R.id.news_title)
        TextView title;
        @BindView(R.id.news_sub_title)
        TextView subTitle;
        @BindView(R.id.news_time)
        TextView time;
        public NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
