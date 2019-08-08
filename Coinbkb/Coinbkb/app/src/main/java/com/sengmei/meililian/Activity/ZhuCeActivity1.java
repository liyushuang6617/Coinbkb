package com.sengmei.meililian.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZhuCeActivity1 extends BaseActivity implements View.OnClickListener {
    private EditText phone, password, repassword, yanzhengma,yaoqing;
    private TextView next, yanzheng,xieyitv,guoji;
    private String Phones, Passwords, rePasswords, Yanzhengmas,types;
    private ImageView yan, yan1;
    private boolean Yans = true, Yans1 = true;
    private boolean xieyi=true;
    private ZhuCeActivity1.TimeCount time;
    private ImageView iv;
    private RefreshDialog dialog = new RefreshDialog(this);

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.zhuce_activity1);
    }

    @Override
    public void initViews() {
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        phone = (EditText) findViewById(R.id.phone);
        yaoqing = (EditText) findViewById(R.id.yaoqing);
        password = (EditText) findViewById(R.id.password);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        repassword = (EditText) findViewById(R.id.repassword);
        repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        guoji = (TextView) findViewById(R.id.guoji);
        guoji.setOnClickListener(this);
        yan = (ImageView) findViewById(R.id.yaniv);
        yan.setOnClickListener(this);
        yan1 = (ImageView) findViewById(R.id.yaniv1);
        yan1.setOnClickListener(this);
        yanzheng = (TextView) findViewById(R.id.yanzheng);
        yanzheng.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        xieyitv=(TextView)findViewById(R.id.xieyitv);
        xieyitv.setOnClickListener(this);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            yanzheng.setText("重新获取");
            yanzheng.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            yanzheng.setClickable(false);//防止重复点击
            yanzheng.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public void ReinitViews() {
        guoji.setText(UserBean.Address);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                Phones = phone.getText().toString().trim();
                Passwords = password.getText().toString().trim();
                rePasswords = repassword.getText().toString().trim();
                Yanzhengmas = yanzhengma.getText().toString().trim();
                if (StringUtil.isBlank(Phones)) {
                    StringUtil.ToastShow(ZhuCeActivity1.this, "手机号不能为空");
                } else if (StringUtil.isBlank(Passwords)) {
                    StringUtil.ToastShow(ZhuCeActivity1.this, "密码不能为空");
                } else if (StringUtil.isBlank(Yanzhengmas)) {
                    StringUtil.ToastShow(ZhuCeActivity1.this, "请输入验证码");
                } else if (!Passwords.equals(rePasswords)) {
                    StringUtil.ToastShow(ZhuCeActivity1.this,"密码不一致");
                } else if (xieyi!=false) {
                    StringUtil.ToastShow(ZhuCeActivity1.this,"请勾选用户协议");
                }else {
                    next.setClickable(false);dialog.showLoading();
                    nextShow();
                }
                break;
            case R.id.xieyitv:
                startActivity(new Intent(ZhuCeActivity1.this,YinSiXieYi1Activity.class));
                break;
            case R.id.guoji:
                startActivity(new Intent(ZhuCeActivity1.this,AddressActivity.class));
                break;
            case R.id.yaniv:
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
            case R.id.yaniv1:
                if (Yans1) {
                    Yans1 = false;
                    //选择状态 显示明文--设置为可见的密码
                    repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan1.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans1 = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan1.setImageResource(R.mipmap.cb_normaled);
                }
                break;
            case R.id.yanzheng:
                Phones = phone.getText().toString().trim();
                Passwords = password.getText().toString().trim();
                if (StringUtil.isMobile(Phones)){
                    time = new ZhuCeActivity1.TimeCount(60000, 1000);
                    time.start();
                    yanzheng.setClickable(false);
                    if (StringUtil.isMobile(Phones)){
                        yanzheng.setClickable(false);
                        YanZhengShow();types="mobile";
                    }else if (StringUtil.isEmail(Phones)){
                        yanzheng.setClickable(false);
                        YXYanZhengShow();types="email";
                    }
                }else {
                    StringUtil.ToastShow(ZhuCeActivity1.this, "请填写正确号码");
                }
                break;
            case R.id.iv:
                if (xieyi==true){
                    xieyi=false;
                    next.setBackgroundResource(R.color.blue);
                    iv.setImageResource(R.mipmap.xieyi);
                }else {
                    xieyi=true;
                    iv.setImageResource(R.mipmap.xieyino);
                    next.setBackgroundResource(R.color.text_blue);
                }
                break;
            default:
                break;
        }
    }

    public void back(View v) {
        finish();
    }

    private void nextShow() {
        String str=yaoqing.getText().toString().trim();
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getZhuCe(Phones, Passwords, rePasswords,Yanzhengmas,str,types,"中国大陆");

        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                next.setClickable(true);
                dialog.hideLoading();
                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    finish();
                    StringUtil.ToastShow1(ZhuCeActivity1.this, response.body().getMessage());
                } else {
                    StringUtil.ToastShow1(ZhuCeActivity1.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                dialog.hideLoading();
                Log.e("AAAAA", "@@11=" + t.getMessage());
                next.setClickable(true);

            }
        });
    }


    //发送手机验证码
    private void  YanZhengShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getyanzhengma(Phones, "regist");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                yanzheng.setClickable(true);
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", response.body().getType()+"重置密码=" + response.body().getMessage());
                StringUtil.ToastShow1(ZhuCeActivity1.this, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11的=" + t.getMessage());
                yanzheng.setClickable(true);
            }
        });
    }
    //发送邮箱验证码
    private void  YXYanZhengShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getyouxiangyanzheng(Phones, "regist");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                yanzheng.setClickable(true);
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", response.body().getType()+"重置1密码=" + response.body().getMessage());
                StringUtil.ToastShow1(ZhuCeActivity1.this, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11的=" + t.getMessage());
                yanzheng.setClickable(true);
            }
        });
    }
}
