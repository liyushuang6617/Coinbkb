package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.DingDanXiangQingBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DingDanXiangQingActivity extends BaseActivity {

    private LinearLayout m, llb;
    private TextView m1, m2, m3, m4, m5, all, titles, title1, panduan, nexts;
    private String Ids, phoneNum;
    private String ids;
    private CustomDialog di;
    private EditText et_dialogContent;
    private TextView yinhangka, kaihuhang, weixin, zhifubao;
    private RelativeLayout yinhangkarl, kaihuhangrl, weixinrl, zhifubaorl;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.dingdanxiangqingactivity);
        Ids = getIntent().getStringExtra("ids");
        TiBiListShow();
    }

    @Override
    public void initViews() {
        m = (LinearLayout) findViewById(R.id.m);
        llb = (LinearLayout) findViewById(R.id.llb);
        titles = (TextView) findViewById(R.id.titles);
        title1 = (TextView) findViewById(R.id.title1);
        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);
        m3 = (TextView) findViewById(R.id.m3);
        m4 = (TextView) findViewById(R.id.m4);
        yinhangka = (TextView) findViewById(R.id.yinhangka);
        kaihuhang = (TextView) findViewById(R.id.kaihuhang);
        weixin = (TextView) findViewById(R.id.weixin);
        zhifubao = (TextView) findViewById(R.id.zhifubao);
        yinhangkarl = (RelativeLayout) findViewById(R.id.yinhangkarl);
        kaihuhangrl = (RelativeLayout) findViewById(R.id.kaihuhangrl);
        weixinrl = (RelativeLayout) findViewById(R.id.weixinrl);
        zhifubaorl = (RelativeLayout) findViewById(R.id.zhifubaorl);
        m5 = (TextView) findViewById(R.id.m5);
        nexts = (TextView) findViewById(R.id.nexts);
        panduan = (TextView) findViewById(R.id.panduan);
        all = (TextView) findViewById(R.id.all);
        nexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                di = new CustomDialog(DingDanXiangQingActivity.this, R.style.customDialog, R.layout.c2cfabudialog_item);
                di.show();
                dia();
            }
        });
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 拨打电话（跳转到拨号界面，用户手动点击拨打）
                 *
                 * @param phoneNum 电话号码
                 */
                if (StringUtil.isBlank(phoneNum)) {
                    StringUtil.ToastShow(DingDanXiangQingActivity.this, "暂时无法拨打");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                startActivity(intent);

            }
        });
    }

    private void TiBiListShow() {
        Log.e("AAIdsAAA", "@@11=" + Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity.this).getService();
        Call<DingDanXiangQingBean> indexdata = mFromServerInterface.getlegal_deal(Ids);
        indexdata.enqueue(new Callback<DingDanXiangQingBean>() {

            @Override
            public void onResponse(Call<DingDanXiangQingBean> call, Response<DingDanXiangQingBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(DingDanXiangQingActivity.this, "请先登录");

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    if (response.body().getMessage().getIs_sure().equals("0")) {
                        titles.setText("未完成");
                        title1.setText("请等待买家付款");
                    } else if (response.body().getMessage().getIs_sure().equals("1")) {
                        titles.setText("已完成");
                        title1.setText("订单已完成，无法查看支付信息");
                    } else if (response.body().getMessage().getIs_sure().equals("2")) {
                        titles.setText("已取消");
                        title1.setText("订单已取消，无法查看支付信息");
                    } else if (response.body().getMessage().getIs_sure().equals("3")) {
                        titles.setText("已付款");
                        title1.setText("已完成付款，请联系商家收款");
                        if (response.body().getMessage().getType().equals("sell")) {
                            nexts.setVisibility(View.VISIBLE);
                        } else {
                            nexts.setVisibility(View.GONE);
                        }
                        //  llb.setBackgroundResource(R.color.black);
                    }
                    if (!StringUtil.isBlank(response.body().getMessage().getType())) {
                        if (response.body().getMessage().getType().equals("sell")) {
                            panduan.setText("买家");
                        } else {
                            panduan.setText("卖家");
                        }
                    }

                    m1.setText(response.body().getMessage().getUser_cash_info().getReal_name());
                    m2.setText("￥" + response.body().getMessage().getPrice());
                    m3.setText(response.body().getMessage().getNumber() + response.body().getMessage().getCurrency_name());
                    m4.setText(response.body().getMessage().getCreate_time());
                    m5.setText(response.body().getMessage().getId());
                    all.setText(response.body().getMessage().getDeal_money() + "  CNY");
                    phoneNum = response.body().getMessage().getUser_cash_info().getAccount_number();
                    if (response.body().getMessage().getIs_sure().equals("3")){
                        yinhangkarl.setVisibility(View.VISIBLE);
                        kaihuhangrl.setVisibility(View.VISIBLE);
                        weixinrl.setVisibility(View.VISIBLE);
                        zhifubaorl.setVisibility(View.VISIBLE);
                    }else {

                        yinhangkarl.setVisibility(View.GONE);
                        kaihuhangrl.setVisibility(View.GONE);
                        weixinrl.setVisibility(View.GONE);
                        zhifubaorl.setVisibility(View.GONE);
                    }
                    yinhangka.setText(response.body().getMessage().getUser_cash_info().getBank_name() + " " + response.body().getMessage().getUser_cash_info().getBank_account());
                    kaihuhang.setText(response.body().getMessage().getBank_address());
                    weixin.setText(response.body().getMessage().getUser_cash_info().getWechat_account());
                    zhifubao.setText(response.body().getMessage().getUser_cash_info().getAlipay_account());
                }
            }

            @Override
            public void onFailure(Call<DingDanXiangQingBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                StringUtil.ToastShow(DingDanXiangQingActivity.this, "请先登录");
            }
        });
    }

    public void dia() {
        LayoutInflater in = LayoutInflater.from(DingDanXiangQingActivity.this);
        View view = in.inflate(R.layout.c2cfabudialog_item, null);
        di.setContentView(view);
        et_dialogContent = (EditText) view.findViewById(R.id.et_dialogContent);
        et_dialogContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView querens = (TextView) view.findViewById(R.id.queren);
        querens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isBlank(et_dialogContent.getText().toString().trim())) {
                    StringUtil.ToastShow1(DingDanXiangQingActivity.this, "密码不能为空");
                    return;
                }
                querenShow(et_dialogContent.getText().toString().trim());

                di.dismiss();
            }
        });
    }

    private void querenShow(final String ps) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_dealsure(Ids, ps);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAIdsAAA", ps + "=@@querenShow=" + Ids);
                Log.e("AAIdsAAA", response.body().getType() + "=@@querenShow=" + response.body().getMessage());
                Toast.makeText(DingDanXiangQingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
