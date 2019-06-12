package com.example.administrator.maura.Tool;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.maura.R;

public class ActionBarView extends LinearLayout {

    private ImageView actionLeft;
    private TextView actionTitle;
    private ImageView actionRight;
    private LinearLayout barView;

    public BarViewOnItemClickListener barViewonItemClickListener;

    public ActionBarView(Context context) {
        super(context);
    }

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View actionBarInflate = LayoutInflater.from(context).inflate(R.layout.actionbar_view, this, true);
        barView = actionBarInflate.findViewById(R.id.bar_view);
        actionLeft = actionBarInflate.findViewById(R.id.barLeft_iv);
        actionTitle = actionBarInflate.findViewById(R.id.barTitle_tv);
        actionRight = actionBarInflate.findViewById(R.id.barRight_iv);

        actionLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barViewonItemClickListener!=null){
                    barViewonItemClickListener.BarViewOnItemClick(0);
                }
            }
        });
        actionRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barViewonItemClickListener!=null){
                    barViewonItemClickListener.BarViewOnItemClick(1);
                }
            }
        });
    }


    public void setBarLeftImg(int path,int actionBarLeftHeight,int actionBarLeftWidth){
        //设置图片参数
        ViewGroup.LayoutParams layoutParams = actionLeft.getLayoutParams();
        layoutParams.height = actionBarLeftHeight;
        layoutParams.width = actionBarLeftWidth;
        actionLeft.setLayoutParams(layoutParams);
        actionLeft.setImageResource(path);
    }

    public void setActionTitle(String vaule,int actionBarTextSize){
        actionTitle.setText(vaule);
        actionTitle.setTextSize(actionBarTextSize);
    }

    public void setActionRight(int path,int actionBarRightHeight,int actionBarRightWidth){
        actionRight.setImageResource(path);
        //设置图片参数
        ViewGroup.LayoutParams layoutParams = actionRight.getLayoutParams();
        layoutParams.height = actionBarRightHeight;
        layoutParams.width = actionBarRightWidth;
        actionRight.setLayoutParams(layoutParams);
    }

    public void setActionBarView(int color,int actionBarViewHeight){
        barView.setBackgroundColor(color);
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.height = actionBarViewHeight;
        layoutParams.width =ViewGroup.LayoutParams.MATCH_PARENT;
        barView.setLayoutParams(layoutParams);
    }

    public void setBarViewOnItemClickListener(BarViewOnItemClickListener barViewonItemClickListener) {
        this.barViewonItemClickListener =  barViewonItemClickListener;
    }

    public interface BarViewOnItemClickListener {
        void BarViewOnItemClick(int position);
    }
}
