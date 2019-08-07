package com.example.checkbox;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity implements RlvAdapter.onClick {

    private RecyclerView mRe;
    private ArrayList<Data> list;
    private RlvAdapter adapter;
    private CheckBox cb;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        sp = getSharedPreferences("checked", MODE_PRIVATE);


        if (sp.getBoolean("status", false)) {
            int p = sp.getInt("ischeck", 0);
//            adapter.sbaState.get(ischeck);
            adapter.sbaState.put(p,true);
        }
    }

    private void initView() {
        mRe = (RecyclerView) findViewById(R.id.re);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Data("张三" + i, false));
        }

        adapter = new RlvAdapter(this, list);
        mRe.setAdapter(adapter);
        mRe.setLayoutManager(new LinearLayoutManager(this));

        adapter.setList(list);
        adapter.setOnClick(this);
    }

    private int pos1;
    @Override
    public void onClick(int pos, Data data) {
        this.pos1 = pos;

    }
}
