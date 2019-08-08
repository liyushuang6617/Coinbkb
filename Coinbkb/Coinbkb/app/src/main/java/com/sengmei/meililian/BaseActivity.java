package com.sengmei.meililian;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.sengmei.kline.R;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BaseActivity extends FragmentActivity {
    /**
     * Context对象
     */
    protected Context mContext;
    /**
     * 所有已存在的Activity
     */
    protected static ConcurrentLinkedQueue<Activity> allActivity = new ConcurrentLinkedQueue<Activity>();
    protected static final int validActivityCount = 15;
    /**
     * 标示
     */
    protected static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Activity队列管理，如果超出指定个数，获取并移除此队列的头（队列中时间最长的）。
        if (allActivity.size() >= validActivityCount) {
            Activity act = allActivity.poll();
            act.finish();// 结束
        }
        allActivity.add(this);
        printAllActivityName();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {//设置布局文件、初始化布局文件中的控件、初始化控件的监听、进行数据初始化、处理其他的数据。（子类重写这些方法）
            setContentView(savedInstanceState);
            initViews();
            initData();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        setStatusBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ReinitViews();
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentView(Bundle savedInstanceState);
    public abstract void initViews();
    public abstract void ReinitViews();
    public abstract void initData();
    private static void printAllActivityName() {
        for (Activity activity : allActivity) {
            Log.e(TAG, activity.getClass().getName());
        }
    }
    /**
     * 退出当前activity
     *
     * @see android.app.Activity#finish()
     */
    public void finish() {
        try {
            super.finish();
            //软键盘隐藏
            if (null != this.getCurrentFocus() && null != this.getCurrentFocus().getWindowToken()) {
                InputMethodManager in = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                in.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            // 从Activity集合中清理出已结束的Activity
            if (allActivity != null && allActivity.size() > 0 && allActivity.contains(this)) {
                allActivity.remove(this);
            }
            for (Activity a : allActivity) {
                Log.e("finish", a.getClass().getName());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 结束所有activity
     */
    public static void finishAll() {
        // 结束Activity
        try {
            for (Activity act : allActivity) {
                allActivity.remove(act);
                act.finish();
                printAllActivityName();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (true) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.transparentblack));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && false) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
