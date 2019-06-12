package com.example.administrator.maura.demochannel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.maura.HomeActivity;
import com.example.administrator.maura.R;
import com.example.administrator.maura.helper.ItemDragHelperCallback;
import com.example.administrator.maura.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecy;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        back = findViewById(R.id.back);
        mRecy = findViewById(R.id.recy);

        init();
    }

    private void init() {
        final List<String> items=new ArrayList<>();
        items.add("头条");
        items.add("社会");
        items.add("国内");

        List<String> otherItems = new ArrayList<>();
        otherItems.add("国际");
        otherItems.add("娱乐");
        otherItems.add("体育");
        otherItems.add("军事");
        otherItems.add("科技");
        otherItems.add("财经");
        otherItems.add("时尚");


        //设置recycleView的显示样式及一行的个数
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        //穿件频道适配器
        final ChannelAdapter adapter = new ChannelAdapter(this, helper, items, otherItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecy.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(ChannelActivity.this, items.get(position), Toast.LENGTH_SHORT).show();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> myChannelList = adapter.getMyChannelList();
                Log.d("zzz","得到适配器中的集合："+myChannelList.size());
                Intent intent = new Intent(ChannelActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("flag","0");
                intent.putStringArrayListExtra("channelVaule", (ArrayList<String>) myChannelList);
                startActivity(intent);
            }
        });
    }
}
