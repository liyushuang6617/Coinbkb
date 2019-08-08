package com.example.wanandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid2.activity.ShouYeActivity;
import com.example.wanandroid2.base.BaseActivtity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivtity {

    @BindView(R.id.mainiv)
    ImageView mainiv;
    @BindView(R.id.maintv)
    TextView maintv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mainanim);
        mainiv.startAnimation(animation);
        maintv.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(MainActivity.this, ShouYeActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
