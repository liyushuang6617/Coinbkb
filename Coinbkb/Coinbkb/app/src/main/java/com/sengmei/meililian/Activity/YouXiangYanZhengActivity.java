package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.E_TiBiBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouXiangYanZhengActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone;
    private TextView next;
    private String Phones, codes;
    private EditText rt_code;
    private Dialog bottomDialog;
    private TextView fasong;
    private TimeCount time;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.youxiangyanzhengactivity);
    }

    @Override
    public void initViews() {
        phone = (EditText) findViewById(R.id.phone);
        // phone.addTextChangedListener(textWatcher);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
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
            Phones = phone.getText().toString().trim();

            if (Phones.length() > 5) {
                next.setClickable(true);
                next.setBackgroundResource(R.color.blue);
            } else {
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
        switch (view.getId()) {
            case R.id.next:
                Phones = phone.getText().toString().trim();
                if (StringUtil.isEmail(Phones)) {
                    FSJYanZhengShow();
                    showDialog1();
                } else {
                    StringUtil.ToastShow(YouXiangYanZhengActivity.this, "邮箱账号错误");
                }

                break;
            case R.id.login:
                finish();
                break;
            default:
        }
    }

    private void showDialog1() {
        bottomDialog = new Dialog(YouXiangYanZhengActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(YouXiangYanZhengActivity.this).inflate(R.layout.youxiangyanzheng_dialog, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        fasong = (TextView) contentView.findViewById(R.id.fasong);
        fasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fasong.getText().toString().trim().equals("发送验证码")){
                    time = new TimeCount(60000, 1000);
                    time.start();
                }
            }
        });
        TextView next = (TextView) contentView.findViewById(R.id.next);
        rt_code = (EditText) contentView.findViewById(R.id.rt_code);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
            }
        });
        fasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FSJYanZhengShow();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PSJYanZhengShow(rt_code.getText().toString().trim());
                codes = rt_code.getText().toString().trim();
                FYXYanZhengShow();
            }
        });
    }

    //发送邮箱验证码
    private void FSJYanZhengShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getyouxiangyanzheng(Phones, "binding");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", "注册=" + response.body().getMessage());
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(YouXiangYanZhengActivity.this, response.body().getMessage());

                    time = new TimeCount(60000, 1000);
                    time.start();
                }else {
                    StringUtil.ToastShow1(YouXiangYanZhengActivity.this, response.body().getMessage());
                    bottomDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            fasong.setText("发送验证码");
            fasong.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            fasong.setClickable(false);//防止重复点击
            fasong.setText(millisUntilFinished / 1000 + "s");
        }
    }
    //邮箱判断验证码
    private void PSJYanZhengShow(final String s) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.youxiangyanzheng(s,Phones);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    Toast.makeText(YouXiangYanZhengActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AAAAA", "注册=" + response.body().getMessage());
                if (response.body().getType().equals("ok")) {
                    codes = s;
                    FYXYanZhengShow();
                } else {
                    StringUtil.ToastShow1(YouXiangYanZhengActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    //绑定邮箱验证码
    private void FYXYanZhengShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.get_email(Phones, codes);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", "注册=" + response.body().getMessage());
                if (response.body().getType().equals("ok")) {

                    bottomDialog.cancel();
                    StringUtil.ToastShow1(YouXiangYanZhengActivity.this, response.body().getMessage());
                    YouXiangYanZhengActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
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