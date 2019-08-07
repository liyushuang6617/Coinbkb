package com.example.wanandroid2.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wanandroid2.R;
import com.example.wanandroid2.adapter.VpShouYeAdapter;
import com.example.wanandroid2.base.BaseActivtity;
import com.example.wanandroid2.fragment.KapFragment;
import com.example.wanandroid2.fragment.MainFragment;
import com.example.wanandroid2.fragment.NavigFragment;
import com.example.wanandroid2.fragment.ProjectFragment;
import com.example.wanandroid2.fragment.PublicFragment;
import com.example.wanandroid2.utils.Constans;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouYeActivity extends BaseActivtity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bnav)
    BottomNavigationView bnav;
    @BindView(R.id.fl)
    FrameLayout fl;
    @BindView(R.id.navig)
    NavigationView navig;
    @BindView(R.id.dl)
    DrawerLayout dl;
    private ArrayList<Fragment> list;
    private ArrayList<Integer> title;
    private VpShouYeAdapter adapter;
    private FragmentManager manager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shou_ye;
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.srl_content_empty, R.string.srl_content_empty);
        dl.addDrawerListener(toggle);
        toggle.syncState();

        addFragments();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl, list.get(0));
        transaction.commit();
        initHeadlayot();
    }

    private void initHeadlayot() {
        View view = View.inflate(this, R.layout.shouye_head_layout, null);
        TextView loginuser = view.findViewById(R.id.loginuser);

    }

    private void addFragments() {
        list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new KapFragment());
        list.add(new NavigFragment());
        list.add(new PublicFragment());
        list.add(new ProjectFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initListener() {
        super.initListener();
        navig.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.collect:
                        break;
                    case R.id.todo:
                        break;
                    case R.id.naight:
                        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if (mode == Configuration.UI_MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            menuItem.setTitle("日间模式");
                            menuItem.setIcon(R.drawable.ic_day);
                        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            menuItem.setTitle("夜间模式");
                        }
                        recreate();
                        break;
                    case R.id.setting:
                        break;
                    case R.id.about:
                        break;
                }
                return false;
            }
        });
        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_main_pager:
                        showFragment(0);
                        break;
                    case R.id.tab_knowledge_hierarchy:
                        showFragment(1);
                        break;
                    case R.id.tab_navigation:
                        showFragment(2);
                        break;
                    case R.id.tab_wx_article:
                        showFragment(3);
                        break;
                    case R.id.tab_project:
                        showFragment(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private int last;

    private void showFragment(int typeHomePager) {
        Fragment fragment = list.get(typeHomePager);
        Fragment lastFragmet = list.get(last);
        FragmentTransaction transaction = manager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl, fragment);
        }
        transaction.show(fragment);
        transaction.hide(lastFragmet);
        transaction.commit();
        last = typeHomePager;
    }
}
