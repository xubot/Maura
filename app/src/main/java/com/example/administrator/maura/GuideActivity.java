package com.example.administrator.maura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.maura.SuperClass.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    String  imgPath="http://pic29.nipic.com/20130520/7487939_204157493112_2.jpg";
    @BindView(R.id.guide_img)
    ImageView guide_img;
    @BindView(R.id.guide_intent_next_tv)
    TextView intent_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ButterKnife.bind(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(imgPath)
                .centerCrop()
                .into(guide_img);

        intent_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,HomeActivity.class));
                finish();
            }
        });
    }
}
