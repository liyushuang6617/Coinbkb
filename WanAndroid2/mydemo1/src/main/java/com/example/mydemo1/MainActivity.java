package com.example.mydemo1;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mydemo1.activity.ShouYeActivity;
import com.example.mydemo1.base.SimpleActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.mainiv)
    ImageView mainiv;
    @BindView(R.id.maintv)
    TextView maintv;

    @Override
    protected void initViewAndData() {
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

    @Override
    protected int createLayout() {
        return R.layout.activity_main;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String url){}


}
