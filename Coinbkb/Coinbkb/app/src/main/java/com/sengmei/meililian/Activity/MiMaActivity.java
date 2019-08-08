package com.sengmei.meililian.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.IndexData;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.MainActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiMaActivity extends BaseActivity implements View.OnClickListener{
    private EditText password,passwords,yaoqing,yanzheng;
    private TextView next,dizhi,xieyitv;
    private String PassWord,PassWords;
    private String zhanghao,yanzhengma,types;
    private ImageView yan,yan2;
    private boolean Yans=true,Yans2=true;
    private boolean xieyi=true;
    private ImageView iv;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.mima_activity);
        zhanghao=getIntent().getStringExtra("zhanghao");
        types=getIntent().getStringExtra("types");
    }

    @Override
    public void initViews() {
        password = (EditText) findViewById(R.id.password);
       // password.addTextChangedListener(textWatcher);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwords = (EditText) findViewById(R.id.passwords);
        passwords.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //passwords.addTextChangedListener(textWatcher);
        yaoqing = (EditText) findViewById(R.id.yaoqing);
      //  yaoqing.addTextChangedListener(textWatcher);
        yanzheng = (EditText) findViewById(R.id.yanzheng);
        //yanzheng.addTextChangedListener(textWatcher);
        next=(TextView)findViewById(R.id.next);
        next.setOnClickListener(this);
        next.setClickable(false);
        xieyitv=(TextView)findViewById(R.id.xieyitv);
        xieyitv.setOnClickListener(this);
        dizhi=(TextView)findViewById(R.id.dizhi);
        dizhi.setOnClickListener(this);
        yan = (ImageView) findViewById(R.id.yan);
        yan.setOnClickListener(this);
        yan2 = (ImageView) findViewById(R.id.yan2);
        yan2.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {
        dizhi.setText(UserBean.Address);
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
            PassWord=password.getText().toString().trim();
            PassWords=passwords.getText().toString().trim();
            yanzhengma=yanzheng.getText().toString().trim();

                    if (PassWord.equals(PassWords)&yanzhengma.length()==4&xieyi==false){
                        next.setClickable(true);

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
                PassWord=password.getText().toString().trim();
                PassWords=passwords.getText().toString().trim();
                yanzhengma=yanzheng.getText().toString().trim();
                if (StringUtil.isBlank(yanzhengma)){
                    StringUtil.ToastShow(MiMaActivity.this,"验证码不能为空");
                }
                if (PassWord.equals(PassWords)&PassWord.length()>0){
                    SheZhiMiMaShow();
                }else {
                    StringUtil.ToastShow(MiMaActivity.this,"密码错误");
                }
                break;
            case R.id.dizhi:
                startActivity(new Intent(MiMaActivity.this,AddressActivity.class));
                break;
            case R.id.xieyitv:
                startActivity(new Intent(MiMaActivity.this,YinSiXieYi1Activity.class));
                break;
            case R.id.yan:
                if (Yans) {
                    Yans = false;
                    //选择状态 显示明文--设置为可见的密码
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_normaled);
                }
                break;
            case R.id.yan2:
                if (Yans2) {
                    Yans2 = false;
                    //选择状态 显示明文--设置为可见的密码
                    passwords.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans2 = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwords.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_normaled);
                }
                break;
            case R.id.iv:
                if (xieyi==true){
                    xieyi=false;
                    next.setBackgroundResource(R.color.blue);
                    iv.setImageResource(R.mipmap.xieyi);
                    next.setClickable(true);
                }else {
                    xieyi=true;
                    iv.setImageResource(R.mipmap.xieyino);
                     next.setBackgroundResource(R.color.text_blue);
                     next.setClickable(false);
                }
                break;
                default:
                    break;
        }
    }

    //设置密码
    private void SheZhiMiMaShow(){
        String str=yaoqing.getText().toString().trim();
        Log.e("AAAAAB", zhanghao+PassWord+PassWords+
                yanzhengma+str+types);
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.getZhuCe(zhanghao,PassWord,PassWords,
                yanzhengma,str,types,"中国大陆");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA",response.body().getType()+"@@="+response.body().getMessage() );
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(MiMaActivity.this, response.body().getMessage());
                    startActivity(new Intent(MiMaActivity.this, LoginActivity.class));
                } else {
                    StringUtil.ToastShow1(MiMaActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
            }
        });
    }
    //设置密码
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