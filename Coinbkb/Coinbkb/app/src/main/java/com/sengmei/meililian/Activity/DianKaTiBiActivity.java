package com.sengmei.meililian.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.TiBiBean;
import com.sengmei.RetrofitHttps.Beans.TiBiXiaLaBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.TiBiLaAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DianKaTiBiActivity extends BaseActivity implements View.OnClickListener {
    private EditText shuluang, mima,addrss_et;
    private TextView next, name, names, keyong, addrss, shouxu,nums,numname,wu;
    private String Ids, Addrsss, Shuluangs, Shouxus, QuanBus, Passwords;
    private boolean Yans = true;
    private String S,Rates;
    private ImageView yan;
    private Spinner spinner;
    private List<String> data_list = new ArrayList<String>();;
    private ArrayAdapter<String> arr_adapter;
    private List<TiBiXiaLaBean.MessageBean> list=new ArrayList<>();
    private ListView listview;
    private boolean Add=true;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.diankatibiactivity);
        infoShow();XiaLaShow();
    }

    @Override
    public void initViews() {
        Ids = getIntent().getStringExtra("currencys");
        nums = (TextView) findViewById(R.id.nums);
        listview = (ListView) findViewById(R.id.listview);
        wu = (TextView) findViewById(R.id.wu);
        numname = (TextView) findViewById(R.id.numname);
        addrss_et = (EditText) findViewById(R.id.addrss_et);
        addrss = (TextView) findViewById(R.id.addrss);
        addrss.setOnClickListener(this);
        findViewById(R.id.ll).setOnClickListener(this);
        mima = (EditText) findViewById(R.id.mima);
        mima.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        shuluang = (EditText) findViewById(R.id.shuluang);
        shuluang.addTextChangedListener(textWatcher);
        shuluang.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        names = (TextView) findViewById(R.id.names);
        shouxu = (TextView) findViewById(R.id.shouxu);
        yan = (ImageView) findViewById(R.id.yan);
        keyong = (TextView) findViewById(R.id.keyong);
        findViewById(R.id.quanbu).setOnClickListener(this);
        findViewById(R.id.yan).setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner);

    }
    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            BigDecimal bg1, bg2,bg3;
            String shuluangs=shuluang.getText().toString().trim();
            if (StringUtil.isBlank(shuluangs)){
                bg1 =new BigDecimal(0);
            }else {
                bg1 =new BigDecimal(shuluangs);
            }
            if (StringUtil.isBlank(Rates)){
                bg2 =new BigDecimal("0");
            }else {
                bg2 =new BigDecimal(Rates);
            }

            double sss1= StringUtil.strToDouble(shuluangs);
            double sss= StringUtil.strToDouble(Rates);
             String ss;
            if (sss1>sss){

                 ss = String.format("%.5f", bg1.subtract(bg2));
            }else {
                ss="0";
            }
            Log.e("AAAAA", bg1+"@@1wqe1=" + bg2);
            Log.e("AAAAA", "@@1wqe1=" + ss);
            nums.setText(ss+" ");
        }
    };
    @Override
    public void ReinitViews() {
        infoShow();XiaLaShow();
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

    public void back(View v) {
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                Add=true;
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.GONE);
                String bei = shuluang.getText().toString().trim();
                String addr = addrss_et.getText().toString().trim();
                Passwords = mima.getText().toString().trim();
                if (bei.length() > 0 & addr.length() > 0 & Passwords.length() > 0) {
                    outShow();
                } else {
                    Toast.makeText(DianKaTiBiActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quanbu:
                Add=true;
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.GONE);
                shuluang.setText(QuanBus);
                break;
            case R.id.ll:
                Add=true;
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.GONE);
                break;
            case R.id.addrss:
                if (Add==false){
                    Add=true;
                    listview.setVisibility(View.GONE);
                    wu.setVisibility(View.GONE);
                }else {
                    Add=false;
                    listview.setVisibility(View.VISIBLE);
                    wu.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.yan:
                Add=true;
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.GONE);
                if (Yans) {
                    Yans = false;
                    //选择状态 显示明文--设置为可见的密码
                    mima.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mima.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_normaled);
                }
                break;
            default:
                break;
        }
    }

    //获取提币信息
    private void infoShow() {
        Ids = getIntent().getStringExtra("currencys");
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DianKaTiBiActivity.this).getService();
        Call<TiBiBean> indexdata = mFromServerInterface.get_info(Ids);
        indexdata.enqueue(new Callback<TiBiBean>() {

            @Override
            public void onResponse(Call<TiBiBean> call, Response<TiBiBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", "@@1wqe1=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    name.setText(response.body().getMessage().getName());
                    names.setText(response.body().getMessage().getName());
                    numname.setText(response.body().getMessage().getName());
                     S=response.body().getMessage().getChange_balance();
                    Log.e("AAAAA", "WWWQ=" +response.body().getMessage().getChange_balance());
                    keyong.setText("" +S+response.body().getMessage().getName());
                    double aA = Double.parseDouble(response.body().getMessage().getRate());
                    shouxu.setText("(" + response.body().getMessage().getRate()+ response.body().getMessage().getName() + ")");
                    Shouxus = response.body().getMessage().getRate();
                    shuluang.setHint("最小提币数量" + response.body().getMessage().getMin_number());
                    QuanBus = response.body().getMessage().getChange_balance();
                    Rates=response.body().getMessage().getRate();
                }
            }


            @Override
            public void onFailure(Call<TiBiBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());
                StringUtil.ToastShow(DianKaTiBiActivity.this,"请先登录");

            }
        });
    }
    private void setList(List<TiBiXiaLaBean.MessageBean> news){
        if (list.size()>0){
            list.clear();
        }

        list.addAll(news);
        //适配器
        TiBiLaAdapter  arr_adapter= new TiBiLaAdapter(DianKaTiBiActivity.this,list);
        //设置样式
      //  arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        listview.setAdapter(arr_adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addrss_et.setText(list.get(position).getAddress());
                listview.setVisibility(View.GONE);Add=true;
                wu.setVisibility(View.GONE);
            }
        });
        /*spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                addrss.setText(data_list.get(arg2));//文本说明
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                addrss.setText("Nothing");
            }
        });*/
    }
    //获提币下拉地址
    private void XiaLaShow() {
        Ids = getIntent().getStringExtra("currencys");
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DianKaTiBiActivity.this).getService();
        Call<TiBiXiaLaBean> indexdata = mFromServerInterface.getTiBiaddress(Ids);
        indexdata.enqueue(new Callback<TiBiXiaLaBean>() {

            @Override
            public void onResponse(Call<TiBiXiaLaBean> call, Response<TiBiXiaLaBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAXiaLaShow", "@@1wqe1=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    if (response.body().getMessage().size()>0){
                        addrss_et.setText(response.body().getMessage().get(0).getAddress());
                        for (int n=0;n<response.body().getMessage().size();n++){
                            data_list.add(response.body().getMessage().get(n).getAddress());
                            setList(response.body().getMessage());
                        }
                    }
                }else {
                    //StringUtil.ToastShow(DianKaTiBiActivity.this,"");
                }
            }


            @Override
            public void onFailure(Call<TiBiXiaLaBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());

            }
        });
    }


    //获取提币信息
    private void outShow() {
        Addrsss = addrss_et.getText().toString().trim();
        Shuluangs = shuluang.getText().toString().trim();
        Passwords = mima.getText().toString().trim();
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DianKaTiBiActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.get_out(Ids, Shuluangs, Shouxus, Addrsss, Passwords);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")){
                    StringUtil.ToastShow1(DianKaTiBiActivity.this, response.body().getMessage());
                    finish();
                }else {
                    StringUtil.ToastShow1(DianKaTiBiActivity.this, response.body().getMessage());
                }
                Log.e("AAAAA", "@@1wqe1=" + response.body().getType());
            }


            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());
                StringUtil.ToastShow(DianKaTiBiActivity.this,"请先登录");
            }
        });
    }

}
