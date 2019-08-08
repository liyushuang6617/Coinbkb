package com.example.wanandroid3.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;


import me.yokeyword.fragmentation.SupportActivity;

public abstract class AbstractSimpleActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createLayout());


    }

    protected abstract int createLayout();
}
