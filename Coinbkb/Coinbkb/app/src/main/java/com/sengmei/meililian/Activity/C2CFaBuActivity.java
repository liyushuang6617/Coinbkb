package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2C_FaBuAdapter;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.C2CTiTleBean;
import com.sengmei.RetrofitHttps.Beans.C2C_FaBuBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.Interfaces.FabuBack;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Fragment.CF_ChuShou;
import com.sengmei.meililian.Fragment.CF_GouMai;
import com.sengmei.meililian.Fragment.C_ChuShou;
import com.sengmei.meililian.Fragment.C_GouMai;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.AddPopWindow;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class C2CFaBuActivity extends BaseActivity implements View.OnClickListener, FabuBack {
    private CF_GouMai bHbg;
    private CF_ChuShou bHadax;
    private TextView[] ButtonText;
    private Fragment[] fragments;
    private TextView goumai, chushou;
    private int index;
    private int currentTabIndex;
    private TextView fabu;
    private ListView listview_buton;
    private RelativeLayout fabu_buton;
    private AddPopWindow addPopWindow;
    private String Ways, Types = "sell", currency_id;
    private Dialog bottomDialog;
    private TextView chushous, qiugou, xuanzhe, names, next;
    private EditText danjia, nums, mins;
    private View va, va1;
    private List<C2CTiTleBean.ObjectBean> listbuton = new ArrayList();
    private C2C_FaBuAdapter mAdapter;
    private double c2cMin=0,c2cMax;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.c2cf_activity);

        goumai = (TextView) findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
        fabu = (TextView) findViewById(R.id.fabu);
        fabu.setVisibility(View.VISIBLE);
        fabu.setOnClickListener(this);
        chushou = (TextView) findViewById(R.id.chushou);
        chushou.setOnClickListener(this);
        bHbg = new CF_GouMai();
        bHadax = new CF_ChuShou();
        fragments = new Fragment[]{bHbg, bHadax};

        getSupportFragmentManager().beginTransaction().add(R.id.content, bHbg)
                .add(R.id.content, bHadax).hide(bHadax)
                .show(bHbg)
                .commit();
    }

    @Override
    public void initViews() {
        addPopWindow = new AddPopWindow(C2CFaBuActivity.this);
        addPopWindow.setFabuBack(this);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

        ButtonText = new TextView[2];
        ButtonText[0] = (TextView) findViewById(R.id.goumai);
        ButtonText[1] = (TextView) findViewById(R.id.chushou);
        ButtonText[0].setSelected(true);
    }

    public void back(View v) {
        finish();
    }

    private void StarColor() {

        goumai.setBackgroundResource(R.color.transparent);
        chushou.setBackgroundResource(R.color.transparent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goumai:
                StarColor();
                index = 0;
                UserBean.C2CMM = "购买";
                break;
            case R.id.chushou:
                StarColor();
                index = 1;
                UserBean.C2CMM = "出售";
                break;
            case R.id.fabu:
                showDialog1();
                break;
            default:
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        ButtonText[currentTabIndex].setSelected(false);
        // set current tab selected
        ButtonText[index].setSelected(true);
        Log.e("index=", index + "");
        ButtonText[index].setBackgroundResource(R.drawable.fabi_top1);
        currentTabIndex = index;
    }

    private void showDialog1() {
        bottomDialog = new Dialog(C2CFaBuActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(C2CFaBuActivity.this).inflate(R.layout.c2c_button, null);
        FaBuDataView();
        chushous = (TextView) contentView.findViewById(R.id.chushou);
        qiugou = (TextView) contentView.findViewById(R.id.qiugou);
        chushous.setOnClickListener(onClickListener);
        qiugou.setOnClickListener(onClickListener);
        xuanzhe = (TextView) contentView.findViewById(R.id.xuanzhe);
        xuanzhe.setOnClickListener(onClickListener);
        names = (TextView) contentView.findViewById(R.id.names);
        va = (View) contentView.findViewById(R.id.va);
        va1 = (View) contentView.findViewById(R.id.va1);
        danjia = (EditText) contentView.findViewById(R.id.danjia);
        nums = (EditText) contentView.findViewById(R.id.nums);
        mins = (EditText) contentView.findViewById(R.id.mins);
        next = (TextView) contentView.findViewById(R.id.next);
        next.setOnClickListener(onClickListener);
        names = (TextView) contentView.findViewById(R.id.names);
        names.setOnClickListener(onClickListener);
        fabu_buton = (RelativeLayout) contentView.findViewById(R.id.fabu_buton);
        fabu_buton.setOnClickListener(onClickListener);
        listview_buton = (ListView) contentView.findViewById(R.id.listview_buton);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = C2CFaBuActivity.this.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next:
                 if (StringUtil.isBlank(nums.getText().toString().trim())||StringUtil.isBlank(mins.getText().toString().trim())){
                     StringUtil.ToastShow(C2CFaBuActivity.this, "最小量或数量不能为空");
                     break;
                }
                    if (Double.parseDouble(nums.getText().toString().trim())<=Double.parseDouble(mins.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity.this, "最小量不能高于数量");
                    } else if (danjia.getText().toString().trim().equals("请选择币种")) {
                        StringUtil.ToastShow(C2CFaBuActivity.this, "请选择币种");
                    } else if (StringUtil.isBlank(danjia.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity.this, "请填写单价");
                    } else if (StringUtil.isBlank(nums.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity.this, "请输入数量");
                    } else if (StringUtil.isBlank(mins.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity.this, "请填写最小交易量");
                    } else {
                        ShangPuFaBuShow();
                        bottomDialog.dismiss();
                    }
                    break;
                case R.id.xuanzhe:
                    addPopWindow.showPopupWindow(xuanzhe);
                    break;
                case R.id.fabu_buton:
                    fabu_buton.setVisibility(View.GONE);
                    break;
                case R.id.chushou:
                    chushous.setTextColor(getResources().getColor(R.color.blue));
                    qiugou.setTextColor(getResources().getColor(R.color.black));
                    va.setBackgroundResource(R.color.blue);
                    va1.setBackgroundResource(R.color.transparent);
                    Types = "sell";
                    break;
                case R.id.qiugou:
                    chushous.setTextColor(getResources().getColor(R.color.black));
                    qiugou.setTextColor(getResources().getColor(R.color.blue));
                    va.setBackgroundResource(R.color.transparent);
                    va1.setBackgroundResource(R.color.blue);
                    Types = "buy";
                    break;
                case R.id.names:
                    fabu_buton.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void Ways(String ways) {

      /*  Ways=ways;
        if (ways.equals("ali_pay")){
            xuanzhe.setText("支付宝");
        }else if (ways.equals("we_chat")){
            xuanzhe.setText("微信");
        }else if (ways.equals("bank")){
            xuanzhe.setText("银行卡");
        }
*/
        Log.e("AAAAA", "@@ways=" + ways);
    }

    //填充list数据
    private void setList(final List<C2CTiTleBean.ObjectBean> news) {
        if (listbuton != null) {
            listbuton.clear();
        }
        listbuton.addAll(news);
        mAdapter = new C2C_FaBuAdapter(C2CFaBuActivity.this, listbuton);
        listview_buton.setAdapter(mAdapter);
        listview_buton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                names.setText(listbuton.get(position).getName());
                if (StringUtil.isBlank(listbuton.get(position).getMax())){
                    c2cMax=0;
                }else {
                    c2cMax= Double.parseDouble(listbuton.get(position).getMax());
                }
                if (StringUtil.isBlank(listbuton.get(position).getMin())){
                    c2cMin=0;
                }else {
                    c2cMin= Double.parseDouble(listbuton.get(position).getMin());
                }
                currency_id = listbuton.get(position).getId();
                fabu_buton.setVisibility(View.GONE);
            }
        });
    }

    //法币币种标题
    private void FaBuDataView() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity.this).getService();
        Call<C2CTiTleBean> indexdata = mFromServerInterface.getURLC2Clist();
        indexdata.enqueue(new Callback<C2CTiTleBean>() {

            @Override
            public void onResponse(Call<C2CTiTleBean> call, Response<C2CTiTleBean> response) {
                if (response.body() == null) {


                    startActivity(new Intent(C2CFaBuActivity.this, LoginActivity.class));
                    return;
                }
                if (response.body().getType().equals("ok")) {

                    setList(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<C2CTiTleBean> call, Throwable t) {
                Log.e("FBETHShow", "@@11=" + t.getMessage());
            }
        });
    }

    //C2C发布
    private void ShangPuFaBuShow() {
        Log.e("ShangPuFaBuShow3", Types+"@@11=" +  danjia.getText().toString().trim());
        Log.e("ShangPuFaBuShow2", nums.getText().toString().trim()+"@@11=" +  mins.getText().toString().trim());
        Log.e("ShangPuFaBuShow1", currency_id);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getpublish(Types, danjia.getText().toString().trim(),
                nums.getText().toString().trim(), mins.getText().toString().trim(), "ali_pay", currency_id);
        indexdata.enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                   // Toast.makeText(C2CFaBuActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("ShangPuFaBuShow", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(C2CFaBuActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(C2CFaBuActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("ShangPuFaBuShow", "@@11=" + t.getMessage());
            }
        });
    }
}
