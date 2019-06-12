package com.example.administrator.maura.SuperClass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 *
 * BaseFragment 中统一处理 Subscription . 防止内存泄漏
 * Created by zhouwei on 16/11/11.
 */
public class BaseFragment  extends Fragment {

    private CompositeSubscription sCompositeSubscription ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()){
            sCompositeSubscription = new CompositeSubscription();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 添加Subscription
     * @param subscription
     */
    public void addSubscription(Subscription subscription){
        Log.d("TAG","add subscription");
        sCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(sCompositeSubscription!=null){
            Log.d("zzz","base activity unscbscribe");
            sCompositeSubscription.unsubscribe();
        }
    }
}
