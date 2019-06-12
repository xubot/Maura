package com.example.administrator.maura;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.administrator.maura.Tool.NavigationView;
import com.example.administrator.maura.news.NewsFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeActivity extends FragmentActivity implements NavigationView.OnItemClickListener{
    @BindView(R.id.navigationView)
    NavigationView navigationView;


    private Fragment newsFragment, trousersFragment,shoeFragment,moreFragment;
    private String[] titles = { "新闻", "裤子", "鞋子","更多" };
    private int[] selectedImage = { R.mipmap.news_select, R.mipmap.photo,
            R.mipmap.wallet,R.mipmap.more };
    private int[] unSelectedImage = { R.mipmap.news_unselect, R.mipmap.photo,
            R.mipmap.wallet,R.mipmap.more};
    private int mHeight;
    private boolean isGetHeight = true;
    private Unbinder bind;
    private String flag="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bind = ButterKnife.bind(this);


    }


    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flag = intent.getStringExtra("flag");
        ArrayList<String> channelVaule = intent.getStringArrayListExtra("channelVaule");
        if(flag.equals("0")&& channelVaule !=null&&channelVaule.size()>0){
            Log.d("zzz","flag"+ flag +"==="+"channelList"+ channelVaule.size());
            showFragment(0,channelVaule);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initView() {
        // 获取屏幕宽度
        Display dm = getWindowManager().getDefaultDisplay();
        final int screenWidth = dm.getWidth();
        // 初始化获取底部导航自身高度
        final ViewTreeObserver vt = navigationView.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isGetHeight) {
                    mHeight = navigationView.getMeasuredHeight();
                    navigationView.setLayout(titles, selectedImage, unSelectedImage, screenWidth, mHeight, HomeActivity.this);
                    navigationView.setColorLing(0);
                    navigationView.setOnItemClickListener(HomeActivity.this);
                    isGetHeight = false;
                }
                return true;
            }
        });
        showFragment(0,null);
    }


    @Override
    public void onItemClick(int position) {
        showFragment(position,null);
    }


    /**
     * 动态添加和显示fragment
     *
     * @param position
     */
    private void showFragment(int position,ArrayList<String> channelList) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.fragment_content, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                //向fragment里面传值
                setConveyFragment(titles[position],newsFragment);
                if(flag.equals("0")&&channelList !=null&&channelList.size()>0){
                    Log.d("zzz","channelList"+channelList.size());
                    setChannelList(channelList,newsFragment);
                }
                break;
            case 1:
                if (trousersFragment == null) {
                    trousersFragment = new TrousersFragment();
                    transaction.add(R.id.fragment_content, trousersFragment);
                } else {
                    transaction.show(trousersFragment);
                }
                break;
            case 2:
                if (shoeFragment == null) {
                    shoeFragment = new ShoeFragment();
                    transaction.add(R.id.fragment_content, shoeFragment);
                } else {
                    transaction.show(shoeFragment);
                }
                break;
            case 3:
                if(moreFragment==null){
                    moreFragment=new MoreFragment();
                    transaction.add(R.id.fragment_content, moreFragment);
                }else {
                    transaction.show(moreFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 向fragment里面传的方法
     */
    private void setConveyFragment(String title,Fragment fragment) {
        Bundle sendBundle = new Bundle();
        sendBundle.putString("fragmentVaule", title);
        fragment.setArguments(sendBundle);
    }

    private void setChannelList(ArrayList<String> channelList,Fragment fragment) {
        Log.d("zzz","channelListsetChannelList"+channelList.size());
        Bundle sendBundle = new Bundle();
        sendBundle.putBoolean("channelReplace",true);
        sendBundle.putStringArrayList("channelVaule", channelList);
        fragment.setArguments(sendBundle);
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }

        if (trousersFragment != null) {
            transaction.hide(trousersFragment);
        }

        if (shoeFragment != null) {
            transaction.hide(shoeFragment);
        }

        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
