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
import com.sengmei.RetrofitHttps.Urls;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
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

public class ZhuCeActivity extends BaseActivity implements View.OnClickListener{
    private EditText phone,rt_code;
    private TextView next,title;
    private String Phones;
    private String codes;
    private String ZhuanHuan="sj";
    private TextView youxiang;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.zhuce_activity);
    }

    @Override
    public void initViews() {
        title = (TextView) findViewById(R.id.title);
        phone = (EditText) findViewById(R.id.phone);
       // phone.addTextChangedListener(textWatcher);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        youxiang=(TextView) findViewById(R.id.youxiang);
        youxiang.setOnClickListener(this);
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

                    if (Phones.length()>10){
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
                Phones=phone.getText().toString().trim();
               // startActivity(new Intent(ZhuCeActivity.this,YanZhengMaActivity.class));
              /*  if (ZhuanHuan.equals("yx")){
                    FYXYanZhengShow();
                }else {
                    FSJYanZhengShow();
                }*/
                if (StringUtil.isBlank(Phones)){
                    Toast.makeText(ZhuCeActivity.this,"请输入手机号或邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isMobile(Phones)){
                    next.setClickable(false);
                    FSJYanZhengShow();
                }else if (StringUtil.isEmail(Phones)){
                    next.setClickable(false);
                    FYXYanZhengShow();
                }else {
                    Toast.makeText(ZhuCeActivity.this,"请输入正确手机号或邮箱",Toast.LENGTH_SHORT).show();
                }
               // showDialog1();  //弹窗
                break;
            case R.id.login:
                finish();
                break;
            case R.id.youxiang:
                Log.e("youxiang","youxiang="+ZhuanHuan );
                if (ZhuanHuan.equals("sj")){
                    title.setText("邮箱注册");
                    phone.setHint("请输入您的邮箱号");
                    ZhuanHuan="yx";
                    youxiang.setText("手机注册");
                    Log.e("youxiang","youxiang1="+ZhuanHuan );
                }else {
                    title.setText("手机注册");
                    phone.setHint("请输入您的手机号");
                    ZhuanHuan="sj";
                    youxiang.setText("邮箱注册");
                    Log.e("youxiang","youxiang2="+ZhuanHuan );
                }
               //startActivity(new Intent(ZhuCeActivity.this,ZhuCeYXActivity.class));
                break;
                default:
        }
    }

  /*  private void showDialog1() {
        final Dialog bottomDialog = new Dialog(ZhuCeActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ZhuCeActivity.this).inflate(R.layout.shoujiyanzheng_dialog, null);
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
    }*/

    //发送手机验证码
    private void FSJYanZhengShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.getyanzhengma(Phones,"regist");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                next.setClickable(true);
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA","注册="+response.body().getMessage() );
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(ZhuCeActivity.this, response.body().getMessage());
                    startActivity(new Intent(ZhuCeActivity.this,MiMaActivity.class)
                            .putExtra("zhanghao",Phones)
                            .putExtra("types","mobile"));
                }else {
                    StringUtil.ToastShow1(ZhuCeActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                next.setClickable(true);
            }
        });
    }
    //发送邮箱验证码
    private void FYXYanZhengShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.getyouxiangyanzheng(Phones,"regist");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                next.setClickable(true);
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAA","注册="+response.body().getMessage() );
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(ZhuCeActivity.this, response.body().getMessage());
                    startActivity(new Intent(ZhuCeActivity.this,MiMaActivity.class)
                            .putExtra("zhanghao",Phones)
                            .putExtra("types","email"));
                }else {
                    StringUtil.ToastShow1(ZhuCeActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                next.setClickable(true);
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
                    Log.e("PSJYanZhengShow", "1111s=" + ss);
                    //  Toast.makeText(getActivity(),"s="+s,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject objectt = new JSONObject(ss);
                        String type = objectt.getString("type");
                        String message = objectt.getString("message");
                        LogUtils.e("@@@@@@=" + message);
                        if (type.equals("ok")) {
                            startActivity(new Intent(ZhuCeActivity.this,MiMaActivity.class)
                                    .putExtra("zhanghao",Phones).putExtra("yanzhengma",codes)
                                    .putExtra("types","mobile"));
                        } else {
                            StringUtil.ToastShow1(ZhuCeActivity.this, message);
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

                map.put("mobile_code", s);
                String result = HttpUtils.post(map,UserBean.URLcheck);
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