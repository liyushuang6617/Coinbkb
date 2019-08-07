package com.example.wanandroid2.utils;

import com.classic.common.MultipleStatusView;

public class Constans {

    private MultipleStatusView multipleStatusView;
    public static boolean isNightMode = false;

    public static final int TYPE_HOME_PAGER = 0;
    public static final int TYPE_KNOWLEDGE = 1;
    public static final int TYPE_NAVIGATION = 2;
    public static final int TYPE_WX_ARTICLE = 3;
    public static final int TYPE_PROJECT = 4;


    public void showNoNetwork() {
        if (multipleStatusView == null) return;
        multipleStatusView.showNoNetwork();
    }
}
