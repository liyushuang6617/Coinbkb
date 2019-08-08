package com.sengmei.meililian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.nineoldandroids.view.ViewHelper;
import com.sengmei.kline.KLineActivity;
import com.sengmei.kline.KLineManager;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Fragment.A_FragMentFaBi;
import com.sengmei.meililian.Fragment.A_FragmentFive;
import com.sengmei.meililian.Fragment.A_FragmentShouYe;
import com.sengmei.meililian.Fragment.A_FragmentBiJiaoYi;
import com.sengmei.meililian.Fragment.A_FragmentHangQing;
import com.sengmei.meililian.Utils.KTititlesBean;
import com.sengmei.meililian.Utils.MenuBack;
import com.sengmei.meililian.Utils.MenuBackTree;
import com.sengmei.meililian.Utils.MenuLeftFragment;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener, MenuBack, MenuBackTree {

    private A_FragmentShouYe FragmentOne;
    private A_FragmentHangQing FragmentTwo;
    private A_FragMentFaBi FragmentFaBi;
    private A_FragmentBiJiaoYi FragmentThree;
    private A_FragmentFive FragmentFive;
    private TextView button01, button02, button03, button04, button05;
    private ImageView home_iv1, home_iv2, home_iv3, home_iv4, home_iv5;

    private Fragment[] fragments;
    private LinearLayout[] mTabs;
    private TextView[] ButtonText;
    private ImageView[] ButtonIV;
    private int[] img = {R.mipmap.shouye, R.mipmap.jiaoyi, R.mipmap.ganggan, R.mipmap.hangqing, R.mipmap.wode};
    private int index;
    private int currentTabIndex;

    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;
    private LinearLayout title_ll;
    private TextView titles;
    private String KTititlesBeanTitles;
    private String UserBeanLegalIDs, UserBeanCurrencyDs, UserBeanLegalNames, UserBeanCurrencyNames;
    private ImageView left_iv;
    private DrawerLayout mDrawerLayout;// 控件对象
    MenuLeftFragment menuLeftFragment = new MenuLeftFragment();

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main1);

        UserBean.Tokens = (String) SharedPreferencesUtil.get(MainActivity.this, DataBean.isNight, "111");
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //changeStatusBarTextColor(true);
        //setStatusBar();
        //加载Fragment
        // MyApplication.Authori = (String) SharedPreferencesUtil.get(MainActivity.this, DataBean.Authori, "");
        FragmentOne = new A_FragmentShouYe();
        FragmentThree = new A_FragmentBiJiaoYi();
        FragmentFaBi = new A_FragMentFaBi();
        FragmentTwo = new A_FragmentHangQing();
        FragmentFive = new A_FragmentFive();

        fragments = new Fragment[]{FragmentOne, FragmentThree, FragmentFaBi, FragmentTwo, FragmentFive};

        getSupportFragmentManager().beginTransaction().add(R.id.content, FragmentOne)
                .add(R.id.content, FragmentThree).hide(FragmentThree)
                .show(FragmentOne)
                .commit();

        menuLeftFragment.setMenuBack(this);
        menuLeftFragment.setMenuBackTree(this);
    }

    @Override
    public void initViews() {
        titles = (TextView) findViewById(R.id.titles);
        title_ll = (LinearLayout) findViewById(R.id.title_ll);
        left_iv = findViewById(R.id.left_iv);
        findViewById(R.id.ivkk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return;
                }
                long curClickTime = System.currentTimeMillis();
                if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;
                    KLineManager.getInstance().mSymbolName = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    KLineManager.getInstance().mCurrencyId = String.valueOf( UserBean.CurrencyDs);
                    KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);
                    KLineActivity.open(MainActivity.this);

                    KTititlesBean.Titles = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    UserBean.HuoQuShow(MainActivity.this, UserBean.CurrencyDs,UserBean.LegalIDs);
                    UserBean.HuoQuShow1(MainActivity.this, UserBean.CurrencyDs,UserBean.LegalIDs);
                    UserBean.FanYongShow(MainActivity.this,UserBean.CurrencyDs,UserBean.LegalIDs);
                    KShow(KTititlesBean.Titles);
                }
            }
        });
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEvents();
                OpenRightMenu(view);
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);// 关闭右侧菜单的滑动出现效果
        button01 = (TextView) findViewById(R.id.button01);
        button02 = (TextView) findViewById(R.id.button02);
        button03 = (TextView) findViewById(R.id.button03);
        button04 = (TextView) findViewById(R.id.button04);
        button05 = (TextView) findViewById(R.id.button05);
        home_iv1 = (ImageView) findViewById(R.id.home_iv1);
        home_iv2 = (ImageView) findViewById(R.id.home_iv2);
        home_iv3 = (ImageView) findViewById(R.id.home_iv3);
        home_iv4 = (ImageView) findViewById(R.id.home_iv4);
        home_iv5 = (ImageView) findViewById(R.id.home_iv5);
        findViewById(R.id.home_button1).setOnClickListener(this);
        findViewById(R.id.home_button2).setOnClickListener(this);
        findViewById(R.id.home_button3).setOnClickListener(this);
        findViewById(R.id.home_button4).setOnClickListener(this);
        findViewById(R.id.home_button5).setOnClickListener(this);

    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void onClick(View view) {
        // Toast.makeText(MainActivity.this,"11",Toast.LENGTH_SHORT).show();
        StarColor();
        switch (view.getId()) {
            case R.id.home_button1:
                title_ll.setVisibility(View.GONE);
                index = 0;
                break;
            case R.id.home_button2:
                title_ll.setVisibility(View.VISIBLE);
                index = 1;
                break;
            case R.id.home_button3:
                title_ll.setVisibility(View.GONE);
                index = 2;
                break;
            case R.id.home_button4:
                title_ll.setVisibility(View.GONE);
                index = 3;
                break;
            case R.id.home_button5:
                if (MyApplication.Authori.equals("")) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return;
                }
                title_ll.setVisibility(View.GONE);
                index = 4;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        Log.e("index=", index + "");
        ButtonText[index].setTextColor(getResources().getColor(R.color.main_blue));
        ButtonIV[index].setImageResource(img[index]);
        currentTabIndex = index;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(" ", "@@@@@@" + DataBean.MunuS);
        if (DataBean.MunuS == 1) {
            title_ll.setVisibility(View.VISIBLE);
            StartInt(DataBean.MunuS);
            DataBean.MunuS = 10;
        } else if (DataBean.MunuS == 0) {
            title_ll.setVisibility(View.GONE);
            StartInt(DataBean.MunuS);
            DataBean.MunuS = 10;
        } else if (DataBean.MunuS == 3) {
            title_ll.setVisibility(View.GONE);
            StartInt(DataBean.MunuS);
            DataBean.MunuS = 10;
        }
    }

    private void StartInt(int ints) {
        StarColor();
        index = ints;
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        Log.e("index=", index + "");
        ButtonText[index].setTextColor(getResources().getColor(R.color.main_blue));
        ButtonIV[index].setImageResource(img[index]);
        currentTabIndex = index;

    }

    @Override
    public void initData() {

        mTabs = new LinearLayout[5];
        mTabs[0] = (LinearLayout) findViewById(R.id.home_button1);
        mTabs[1] = (LinearLayout) findViewById(R.id.home_button2);
        mTabs[2] = (LinearLayout) findViewById(R.id.home_button3);
        mTabs[3] = (LinearLayout) findViewById(R.id.home_button4);
        mTabs[4] = (LinearLayout) findViewById(R.id.home_button5);
        // select first tab
        mTabs[0].setSelected(true);
        ButtonText = new TextView[5];
        ButtonText[0] = (TextView) findViewById(R.id.button01);
        ButtonText[1] = (TextView) findViewById(R.id.button02);
        ButtonText[2] = (TextView) findViewById(R.id.button03);
        ButtonText[3] = (TextView) findViewById(R.id.button04);
        ButtonText[4] = (TextView) findViewById(R.id.button05);
        ButtonText[0].setSelected(true);
        ButtonIV = new ImageView[5];
        ButtonIV[0] = (ImageView) findViewById(R.id.home_iv1);
        ButtonIV[1] = (ImageView) findViewById(R.id.home_iv2);
        ButtonIV[2] = (ImageView) findViewById(R.id.home_iv3);
        ButtonIV[3] = (ImageView) findViewById(R.id.home_iv4);
        ButtonIV[4] = (ImageView) findViewById(R.id.home_iv5);
        ButtonIV[0].setSelected(true);

    }

    private void StarColor() {
        button01.setTextColor(getResources().getColor(R.color.button_text_no));
        button02.setTextColor(getResources().getColor(R.color.button_text_no));
        button03.setTextColor(getResources().getColor(R.color.button_text_no));
        button04.setTextColor(getResources().getColor(R.color.button_text_no));
        button05.setTextColor(getResources().getColor(R.color.button_text_no));
        home_iv1.setImageResource(R.mipmap.shouye_no);
        home_iv2.setImageResource(R.mipmap.jiaoyi_no);
        home_iv3.setImageResource(R.mipmap.ganggan_no);
        home_iv4.setImageResource(R.mipmap.hangqing_no);
        home_iv5.setImageResource(R.mipmap.wode_no);
    }

    private void KShow(String title) {
        UserBean.titleKid = title;
        UserBean.KLineShow0(MainActivity.this, title);
        //  UserBean.KLineShow(MainActivity.this, title);
        UserBean.KLineShow5(MainActivity.this, title);
        UserBean.KLineShow30(MainActivity.this, title);
        UserBean.KLineShow1h(MainActivity.this, title);
        UserBean.KLineShow1d(MainActivity.this, title);
        UserBean.KLineShow1w(MainActivity.this, title);
        UserBean.KLineShow1m(MainActivity.this, title);

        //startActivity(new Intent(MainActivity.this, KLineActivity.class));
    }

    /**
     * 初始化DrawerLayout事件监听
     */
    private void initEvents() {
        // 设置监听
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals(
                        getResources().getString(R.string.left_tag))) {// 展开左侧菜单
                    float leftScale = 1 - 0.3f * scale;

                    // 设置左侧菜单缩放效果
                    /*ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
*/
                    // 设置中间View缩放效果
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else {// 展开右侧菜单
                    // 设置中间View缩放效果
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() * 3 / 4);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            // 菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    /**
     * 打开右侧菜单
     *
     * @param view
     */
    public void OpenRightMenu(View view) {

        mDrawerLayout.setVisibility(View.VISIBLE);
        mDrawerLayout.openDrawer(Gravity.LEFT);// 展开侧边的菜单
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);// 打开手势滑动

    }

    @Override
    public void Names(String str) {
        LogUtils.e("测试=" + str+"=UserBean.JiaoYIMM=" + UserBean.JiaoYIMM);
        if (UserBean.JiaoYIMM==1){
        }else {
            KTititlesBean.Titles=str;
        }

        titles.setText(KTititlesBean.Titles);
        KLineManager.getInstance().mSymbolName=str;

    }

    @Override
    public void IsClick(boolean b) {
        LogUtils.e("MeNuuuu=" + "onCreateView=" + b);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerLayout.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });

        if (b == false) {

        }
    }

    @Override
    public void Ids(int ids, int idsvice) {
        Log.e("KKKSSDSDK", UserBean.JiaoYIMM+"=="+ids+"@@@@@@" + idsvice);
      /*  Currency_nameid = ids + "";
        Legal_nameid = idsvice + "";*/

        UserBeanLegalIDs=""+ids;
        UserBeanCurrencyDs=""+idsvice;

    }

    @Override
    public void NamesThree(String Names, String Namesvice) {
        Log.e("KKKSSDSDK", UserBean.JiaoYIMM+"=="+DataBean.id2+"@@@@@@" + DataBean.id1);

        UserBeanLegalNames=""+Names;
        UserBeanCurrencyNames=""+Namesvice;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //exit();
            // startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
