package com.sengmei.meililian.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.MainActivity;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText phone, password,yanzhengma;
    private TextView login,yanzheng;
    private String Phones, Passwords,Yanzhengmas;
    private ImageView dele,yan,delet;
    private boolean Yans = true;
    private TimeCount time;
    private RefreshDialog dialog = new RefreshDialog(this);

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
    }

    @Override
    public void initViews() {
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);
        findViewById(R.id.zhuce).setOnClickListener(this);
        dele = (ImageView) findViewById(R.id.dele);
        yan = (ImageView) findViewById(R.id.yan);
        delet = (ImageView) findViewById(R.id.delet);
        yan = (ImageView) findViewById(R.id.yan);
        yan.setOnClickListener(this);
        yanzheng = (TextView) findViewById(R.id.yanzheng);
        yanzheng.setOnClickListener(this);
        String zhanghao= (String) SharedPreferencesUtil.get(LoginActivity.this,DataBean.ZhangHao,"");
        if (!StringUtil.isBlank(zhanghao)){
            phone.setText(zhanghao);
        }
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
        MyApplication.Authori="";
        SharedPreferences sp = getSharedPreferences("DATA",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DataBean.Authori, "");
        editor.commit();
        SharedPreferencesUtil.put(LoginActivity.this,DataBean.Authori,"");
     /*   password.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    // 获得焦点

                } else {

                    // 失去焦点

                }

            }


        });*/
    }
 /* View.OnFocusChangeListener onFocusChangeListener=new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {

      }
  };*/
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

    public void back(View v) {
        DataBean.MunuS=0;
        finish();
    }
    private void LoginShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.Login(Phones,Passwords,"1");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                login.setClickable(true);
                dialog.hideLoading();
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    DataBean.Types=response.body().getType();
                    MyApplication.Authori=response.body().getMessage();
                    SharedPreferences sp = getSharedPreferences("DATA",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(DataBean.Authori,response.body().getMessage());
                    editor.commit();
                    SharedPreferencesUtil.put(LoginActivity.this,DataBean.Authori,response.body().getMessage());
                    StringUtil.ToastShow1(LoginActivity.this, "登录成功");
                    SharedPreferencesUtil.put(LoginActivity.this,DataBean.ZhangHao,Phones);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else {
                    StringUtil.ToastShow1(LoginActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                dialog.hideLoading();
                Log.e("AAAAA","@@11="+t.getMessage() );
                login.setClickable(true);

            }
        });
    }


    //发送手机验证码
    private void FSJYanZhengShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.getyanzhengma(Phones,"login");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                yanzheng.setClickable(true);
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA","注册="+response.body().getMessage() );
                StringUtil.ToastShow1(LoginActivity.this, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA","@@11的="+t.getMessage() );
                yanzheng.setClickable(true);
            }
        });
    }
    //发送邮箱验证码
    private void FYXYanZhengShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<ZhuCeBean> indexdata=mFromServerInterface.getyouxiangyanzheng(Phones,"login");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                yanzheng.setClickable(true);
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAA","注册="+response.body().getMessage() );
                StringUtil.ToastShow1(LoginActivity.this, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                yanzheng.setClickable(true);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                Phones=phone.getText().toString().trim();
                Passwords=password.getText().toString().trim();
                Yanzhengmas=yanzhengma.getText().toString().trim();
                Log.e("login="+Phones,"login=" +Passwords);
                if (StringUtil.isBlank(Phones)){
                    StringUtil.ToastShow(LoginActivity.this,"账号不能为空");
                }else if (StringUtil.isBlank(Passwords)){
                            StringUtil.ToastShow(LoginActivity.this,"密码不能为空");
                }else if(StringUtil.isMobile(Phones)|StringUtil.isEmail(Phones)){
                /*    if (Yanzhengmas.length()>0){
                        login.setClickable(false);dialog.showLoading();
                        LoginShow();
                    }else {
                        StringUtil.ToastShow(LoginActivity.this,"请输入验证码");
                    }*/
                    login.setClickable(false);dialog.showLoading();
                    LoginShow();
                }else {
                    StringUtil.ToastShow(LoginActivity.this,"请输入正确账号");
                }
                break;
            case R.id.zhuce:
                startActivity(new Intent(LoginActivity.this,ZhuCeActivity1.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgetActivity1.class));
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
            case R.id.yanzheng:
                Phones=phone.getText().toString().trim();
                Passwords=password.getText().toString().trim();

                if (StringUtil.isMobile(Phones)){
                    time = new TimeCount(60000, 1000);
                    time.start();
                    yanzheng.setClickable(false);
                    FSJYanZhengShow();
                }else if (StringUtil.isEmail(Phones)){
                    time = new TimeCount(60000, 1000);
                    time.start();
                    yanzheng.setClickable(false);
                    FYXYanZhengShow();
                }else {
                    Toast.makeText(LoginActivity.this,"请输入正确手机号或邮箱",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }
    private static boolean isExit = false;
    //主线程处理视图，isExit默认为false，就是点击第一次时，弹出"再按一次退出程序"
    //点击第二次时关闭应用
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //exit();
            // startActivity(new Intent(LoginActivity.this,MainActivity.class));
            MyApplication.Authori="";
            SharedPreferences sp = getSharedPreferences("DATA",0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(DataBean.Authori,"");
            editor.commit();
            SharedPreferencesUtil.put(LoginActivity.this,DataBean.Authori,"");
            DataBean.MunuS=0;
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

   /* *
     * 点击两次退出程序*/

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            //参数用作状态码；根据惯例，非 0 的状态码表示异常终止。
            System.exit(0);
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