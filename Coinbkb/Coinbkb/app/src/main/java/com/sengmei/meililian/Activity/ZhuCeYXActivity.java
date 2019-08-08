package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.sengmei.meililian.Adapter.E_TiBiDiZhiAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.E_TiBiDiZhiBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;
import com.sengmei.meililian.Utils.XHttpUils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZhuCeYXActivity extends BaseActivity implements View.OnClickListener{
    private EditText phone,rt_code;
    private TextView next;
    private String Phones;
    private String codes;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.zhuceyx_activity);
    }

    @Override
    public void initViews() {
        phone = (EditText) findViewById(R.id.phone);
       // phone.addTextChangedListener(textWatcher);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.shouji).setOnClickListener(this);
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
            Phones=phone.getText().toString().trim();

                    if (Phones.length()>5){
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
                if (StringUtil.isMobile(Phones)){
                    showDialog1();

                    FSJYanZhengShow();
                }else {
                    StringUtil.ToastShow(ZhuCeYXActivity.this,"请填写正确手机号");
                }

                //startActivity(new Intent(ZhuCeYXActivity.this,YanZhengMaActivity.class));
                break;
            case R.id.login:
                finish();
                break;
            case R.id.shouji:
                finish();
                break;
                default:
        }
    }

    private void showDialog1() {
        final Dialog bottomDialog = new Dialog(ZhuCeYXActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ZhuCeYXActivity.this).inflate(R.layout.youxiangyanzheng_dialog, null);
        TextView quxiao=(TextView) contentView.findViewById(R.id.quxiao);
        TextView fasong=(TextView) contentView.findViewById(R.id.fasong);
        TextView next=(TextView) contentView.findViewById(R.id.next);
        rt_code=(EditText)contentView.findViewById(R.id.rt_code);
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
                codes=rt_code.getText().toString().trim();
                PSJYanZhengShow(codes);
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
                    StringUtil.ToastShow1(ZhuCeYXActivity.this, response.body().getMessage());
                } else {
                    StringUtil.ToastShow1(ZhuCeYXActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }
    //邮箱判断验证码
    private void PSJYanZhengShow(final String s) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String ss = msg.obj.toString().trim();
                    Log.e("WoDeShow1", "1111s=" + ss);
                    //  Toast.makeText(getActivity(),"s="+s,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject objectt = new JSONObject(ss);
                        String type = objectt.getString("type");
                        String message = objectt.getString("message");
                        LogUtils.e("@@@@@@=" + message);
                        if (type.equals("ok")) {
                            startActivity(new Intent(ZhuCeYXActivity.this,MiMaActivity.class)
                                    .putExtra("zhanghao",Phones).putExtra("yanzhengma",codes)
                                    .putExtra("types","email"));
                        } else {
                            StringUtil.ToastShow1(ZhuCeYXActivity.this, message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 启动线程来执行任务
        new Thread() {
            public void run() {
                // 请求网络
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("email_code", s);
                String result = HttpUtils.post(map,UserBean.URLemail);
                Message m = new Message();
                m.what = 1;
                m.obj = result;
                // 发送消息到Handler
                handler.sendMessage(m);
            }
        }.start();
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