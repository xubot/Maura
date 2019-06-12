package com.example.administrator.maura.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.administrator.maura.R;
import com.example.administrator.maura.SuperClass.BaseFragment;
import com.example.administrator.maura.TabLayout.MyTabLoyoutPagerAdapter;
import com.example.administrator.maura.Tool.ActionBarView;
import com.example.administrator.maura.Tool.CommonMethod;
import com.example.administrator.maura.demochannel.ChannelActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends BaseFragment implements ActionBarView.BarViewOnItemClickListener
                                                         ,OnTabSelectListener,ViewPager.OnPageChangeListener {
    @BindView(R.id.action_bar)
    ActionBarView actionBarVeiw;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tl)
    SlidingTabLayout tabLayout;

    private Unbinder binder;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};

    private MyTabLoyoutPagerAdapter mAdapter;
    private boolean channelReplace=false;
    private ArrayList<String> channelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container,false);
        binder = ButterKnife.bind(this, view);

        //得到HomeActivity传来给actionBar赋值的值
        Bundle bundle = getArguments();
        if(null != bundle){
            String vaule = bundle.getString("fragmentVaule");
            //设置actionBar的方法
            setActionBarView(vaule);
        }

        //动态添加展示的fragment 并将参数传过去
        for (int i=0;i<mTitles.length;i++) {
            NewsTabLouyoutVauleFragment newsTabLouyoutVauleFragment = new NewsTabLouyoutVauleFragment();
            Bundle bundleNews = new Bundle();
            bundleNews.putString("tabValue",mTitles[i]);
            newsTabLouyoutVauleFragment.setArguments(bundleNews);
            mFragments.add(newsTabLouyoutVauleFragment);
        }

        //创建viewpager的适配器
        mAdapter = new MyTabLoyoutPagerAdapter(getChildFragmentManager(),mFragments,mTitles);
        vp.setAdapter(mAdapter);
        vp.setOnPageChangeListener(this);

        //给tabLayout进行关联viewPager
        tabLayout.setViewPager(vp, mTitles, getActivity(), mFragments);
        //进行点击监听
        tabLayout.setOnTabSelectListener(this);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }else {
            //得到HomeActivity传来给actionBar赋值的值
            Bundle bundle = getArguments();
            if(null != bundle){
                String vaule = bundle.getString("fragmentVaule");
                channelList = bundle.getStringArrayList("channelVaule");
                channelReplace = bundle.getBoolean("channelReplace", false);
                if(channelList!=null&&channelList.size()>0&&channelReplace){
                    Log.d("zzz",channelList.size()+"===="+channelReplace);
                    //int size=channelList.size();
                    //String[] array =channelList.toArray(new String[size]);

                    String[] array = new String[channelList.size()];
                    // List转换成数组
                    for (int i = 0; i < channelList.size(); i++) {
                        array[i] = channelList.get(i);
                    }

                    Log.d("zzz",array.length+"");
                    //动态添加展示的fragment 并将参数传过去
                    for (int i=0;i<channelList.size();i++) {
                        Log.d("zzz",channelList.get(i));
                        NewsTabLouyoutVauleFragment newsTabLouyoutVauleFragment = new NewsTabLouyoutVauleFragment();
                        Bundle bundleNews = new Bundle();
                        bundleNews.putString("tabValue",channelList.get(i));
                        newsTabLouyoutVauleFragment.setArguments(bundleNews);
                        mFragments.add(newsTabLouyoutVauleFragment);
                    }

                    //创建viewpager的适配器
                    mAdapter = new MyTabLoyoutPagerAdapter(getChildFragmentManager(),mFragments,array);
                    vp.setAdapter(mAdapter);
                    vp.setOnPageChangeListener(this);

                    //给tabLayout进行关联viewPager
                    tabLayout.setViewPager(vp, array, getActivity(), mFragments);
                    //进行点击监听
                    tabLayout.setOnTabSelectListener(this);
                }
                //设置actionBar的方法
                setActionBarView(vaule);
            }
        }
    }

    //设置导航栏
    private void setActionBarView(String vaule) {
        actionBarVeiw.setActionBarView(this.getResources().getColor(R.color.colorAccent),CommonMethod.dip2px(getActivity(),35));
        actionBarVeiw.setBarLeftImg(R.mipmap.sideslip,CommonMethod.dip2px(getActivity(),25),CommonMethod.dip2px(getActivity(),25));
        actionBarVeiw.setActionTitle(vaule,20);
        actionBarVeiw.setActionRight(R.mipmap.more,CommonMethod.dip2px(getActivity(),25),CommonMethod.dip2px(getActivity(),25));
        actionBarVeiw.setBarViewOnItemClickListener(this);
    }

    //actionBar的点击事件
    @Override
    public void BarViewOnItemClick(int position) {
        switch (position){
            case 0:
                Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),ChannelActivity.class));
                break;
        }
    }


    /**
     * 下面是tab的点击方法
     */
    //第一次点击导航时的方法
    @Override
    public void onTabSelect(int position) {
        //Log.d("zzz","onTabSelect&position--->"+position+"==="+mTitles[position]);
    }

    //重复点击导航时的方法
    @Override
    public void onTabReselect(int position) {
        Log.d("zzz","onTabReselect&position--->"+position+"==="+mTitles[position]);
        //重复点击是如何处理
    }
    /**
     * 上面是tab的点击方法
     */

    /**
     * 下面是viewpager的滑动监听的方法
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //Toast.makeText(getActivity(), "正在滑动"+i+"--"+v+"=="+i1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int i) {
        Log.d("zzz","切换结束,当前切换后的角标"+i+"--"+mTitles[i]);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        switch (i){
            case 0:
                Log.d("zzz","按下");
                break;
            case 1:
                Log.d("zzz","移动");
                break;
            case 2:
                Log.d("zzz","抬起");
                break;
        }
    }
    /**
     * 上面是viewpager的滑动监听的方法
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }
}
