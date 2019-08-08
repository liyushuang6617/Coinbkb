package com.sengmei.meililian.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;


public class YanZhengMaActivity extends BaseActivity implements View.OnClickListener{
    private EditText yanzhengma;
    private TextView next;
    private String Yanzhengmas;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.yanzhengma_activity);
    }

    @Override
    public void initViews() {
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        yanzhengma.addTextChangedListener(textWatcher);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {
        findViewById(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Yanzhengmas=yanzhengma.getText().toString().trim();

                    if (Yanzhengmas.length()==4){
                    next.setClickable(true);
                        next.setBackgroundResource(R.color.blue);
                }else {
                        next.setClickable(false);
                        next.setBackgroundResource(R.color.login);
                }


        }
    };

    public void back(View v) {
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                startActivity(new Intent(YanZhengMaActivity.this,MiMaActivity.class));
                break;
                default:
        }
    }
}

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛